package com.employee;

import java.util.List;

public class AccessResult {
    String status;
    List<String> reasons;

    public AccessResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getReasons() {
        return reasons;
    }
}