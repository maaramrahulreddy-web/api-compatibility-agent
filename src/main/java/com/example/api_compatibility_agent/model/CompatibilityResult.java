package com.example.api_compatibility_agent.model;

public class CompatibilityResult {

    private String status;
    private String reason;

    public CompatibilityResult() {
    }

    public CompatibilityResult(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}