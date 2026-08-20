public class OrderManagementQA {

    static int passed = 0;
    static int failed = 0;

    static void check(String testName, double actual, double expected) {

        if (Math.abs(actual - expected) < 0.01) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName
                    + " | Expected: " + expected
                    + " | Actual: " + actual);
            failed++;
        }
    }

    static void checkException(String testName, String expectedMessage,
                               OrderManagement.Product[] products,
                               String coupon) {

        try {
            OrderManagement.calculateOrder(products, coupon);

            System.out.println("FAIL: " + testName
                    + " | Expected exception");
            failed++;

        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains(expectedMessage)) {
                System.out.println("PASS: " + testName);
                passed++;
            } else {
                System.out.println("FAIL: " + testName
                        + " | Wrong exception: " + e.getMessage());
                failed++;
            }
        }
    }

    public static void main(String[] args) {

        // 1. Single product
        OrderManagement.Product p1 =
                new OrderManagement.Product(
                        "P101", "Electronics",
                        1, 500, 10, 18);

        check(
                "Single product",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p1}, "NONE"),
                581.0
        );


        // 2. Multiple products
        OrderManagement.Product p2 =
                new OrderManagement.Product(
                        "P102", "Clothing",
                        1, 300, 5, 18);

        OrderManagement.Product p2Multi =
                new OrderManagement.Product(
                        "P101", "Electronics",
                        2, 500, 10, 18);

        check(
                "Multiple products",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p2Multi, p2},
                        "NONE"),
                1398.30
        );


        // 3. Zero quantity
        OrderManagement.Product p3 =
                new OrderManagement.Product(
                        "P101", "Electronics",
                        0, 500, 10, 18);

        check(
                "Zero quantity",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p3}, "NONE"),
                50.0
        );


        // 4. Negative quantity
        OrderManagement.Product p4 =
                new OrderManagement.Product(
                        "P101", "Electronics",
                        -1, 500, 10, 18);

        checkException(
                "Negative quantity",
                "Negative quantity",
                new OrderManagement.Product[]{p4},
                "NONE"
        );


        // 5. Invalid product
        OrderManagement.Product p5 =
                new OrderManagement.Product(
                        "INVALID", "Electronics",
                        1, 500, 10, 18);

        checkException(
                "Invalid product",
                "Invalid product",
                new OrderManagement.Product[]{p5},
                "NONE"
        );


        // 6. Invalid coupon
        checkException(
                "Invalid coupon",
                "Invalid coupon",
                new OrderManagement.Product[]{p1},
                "WRONG"
        );


        // 7. Valid coupon
        check(
                "Valid coupon",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p1}, "SAVE10"),
                522.0
        );


        // 8. No coupon
        check(
                "No coupon",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p1}, "NONE"),
                581.0
        );


        // 9. Maximum discount
        OrderManagement.Product p9 =
                new OrderManagement.Product(
                        "P101", "Electronics",
                        10, 500, 50, 18);

        check(
                "Maximum discount",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p9}, "NONE"),
                4130.0
        );


        // 10. Tax calculation
        OrderManagement.Product p10 =
                new OrderManagement.Product(
                        "P104", "Books",
                        1, 100, 0, 18);

        check(
                "Tax calculation",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p10}, "NONE"),
                168.0
        );


        // 11. Free shipping
        OrderManagement.Product p11 =
                new OrderManagement.Product(
                        "P103", "Food",
                        3, 500, 0, 18);

        check(
                "Free shipping",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p11}, "NONE"),
                1770.0
        );


        // 12. Bulk order
        OrderManagement.Product p12 =
                new OrderManagement.Product(
                        "P102", "Clothing",
                        10, 100, 0, 18);

        check(
                "Bulk order",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p12}, "NONE"),
                1171.0
        );


        // 13. Out of stock
        OrderManagement.Product p13 =
                new OrderManagement.Product(
                        "P104", "Books",
                        6, 100, 0, 18);

        checkException(
                "Out of stock",
                "Out of stock",
                new OrderManagement.Product[]{p13},
                "NONE"
        );


        // 14. Multiple products + coupon
        check(
                "Multiple products with coupon",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p2Multi, p2},
                        "SAVE10"),
                1244.90
        );


        // 15. High quantity
        OrderManagement.Product p15 =
                new OrderManagement.Product(
                        "P103", "Food",
                        20, 50, 5, 18);

        check(
                "High quantity",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p15}, "NONE"),
                1112.0
        );


        // 16. Low-value order
        OrderManagement.Product p16 =
                new OrderManagement.Product(
                        "P104", "Books",
                        1, 100, 0, 18);

        check(
                "Low-value order",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p16}, "NONE"),
                168.0
        );


        // 17. Large order
        OrderManagement.Product p17 =
                new OrderManagement.Product(
                        "P103", "Food",
                        20, 100, 10, 18);

        check(
                "Large order",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p17}, "NONE"),
                2006.0
        );


        // 18. Two product categories
        check(
                "Two categories",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p2Multi, p2},
                        "NONE"),
                1398.30
        );


        // 19. Coupon + bulk order
        check(
                "Coupon with bulk order",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p12},
                        "SAVE10"),
                1053.0
        );


        // 20. Complete order
        check(
                "Complete order",
                OrderManagement.calculateOrder(
                        new OrderManagement.Product[]{p2Multi, p2, p15},
                        "SAVE10"),
                2188.90
        );


        // Final result
        System.out.println();
        System.out.println("==============================");
        System.out.println("QA TEST RESULTS");
        System.out.println("==============================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("==============================");

        // Jenkins build fails if any QA test fails
        if (failed > 0) {
            System.exit(1);
        }
    }
}