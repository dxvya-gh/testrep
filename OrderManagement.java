import java.util.*;

public class OrderManagement {

    static class Product {
        String productId;
        String category;
        int quantity;
        double unitPrice;
        double discount;
        double tax;

        Product(String productId, String category, int quantity,
                double unitPrice, double discount, double tax) {
            this.productId = productId;
            this.category = category;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.discount = discount;
            this.tax = tax;
        }
    }

    // Available products and their stock
    static Map<String, Integer> stock = new HashMap<>();

    static {
        stock.put("P101", 10);
        stock.put("P102", 20);
        stock.put("P103", 50);
        stock.put("P104", 5);
    }

    public static double calculateOrder(Product[] products, String coupon) {

        double subtotal = 0;
        double totalDiscount = 0;

        // Calculate subtotal and product/category discounts
        for (Product p : products) {

            // Invalid product
            if (!stock.containsKey(p.productId)) {
                throw new IllegalArgumentException("Invalid product: " + p.productId);
            }

            // Negative quantity
            if (p.quantity < 0) {
                throw new IllegalArgumentException("Negative quantity");
            }

            // Zero quantity
            if (p.quantity == 0) {
                continue;
            }

            // Out of stock
            if (p.quantity > stock.get(p.productId)) {
                throw new IllegalArgumentException("Out of stock: " + p.productId);
            }

            double productTotal = p.quantity * p.unitPrice;

            subtotal += productTotal;

            // Category-specific discount
            totalDiscount += productTotal * p.discount / 100.0;

            // Bulk-order discount
            if (p.quantity >= 10) {
                totalDiscount += productTotal * 5.0 / 100.0;
            }
        }

        // Coupon discount
        if (coupon != null && !coupon.equals("NONE")) {

            if (coupon.equals("SAVE10")) {
                totalDiscount += subtotal * 10.0 / 100.0;
            } else {
                throw new IllegalArgumentException("Invalid coupon");
            }
        }

        // Maximum discount = 30% of subtotal
        double maximumDiscount = subtotal * 30.0 / 100.0;

        if (totalDiscount > maximumDiscount) {
            totalDiscount = maximumDiscount;
        }

        // Amount after discounts
        double taxableAmount = subtotal - totalDiscount;

        // GST
        double gst = taxableAmount * 18.0 / 100.0;

        // Free shipping if order value is at least ₹1000
        double shipping;

        if (taxableAmount >= 1000) {
            shipping = 0;
        } else {
            shipping = 50;
        }

        double finalAmount = taxableAmount + gst + shipping;

        return finalAmount;
    }

    public static void main(String[] args) {

        Product[] products = {
                new Product("P101", "Electronics",
                        2, 500, 10, 18),

                new Product("P102", "Clothing",
                        1, 300, 5, 18)
        };

        try {
            double amount = calculateOrder(products, "SAVE10");

            System.out.printf("Final Order Amount: %.2f%n", amount);

        } catch (IllegalArgumentException e) {
            System.out.println("Order Error: " + e.getMessage());
        }
    }
}