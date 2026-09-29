package com.example.api_compatibility_agent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ApiChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "api_endpoint_id")
    private ApiEndpoint apiEndpoint;

    private String changeType;

    private String description;

    private String oldValue;

    private String newValue;

    public ApiChange() {
    }

    public ApiChange(ApiEndpoint apiEndpoint, String changeType, String description,
                     String oldValue, String newValue) {
        this.apiEndpoint = apiEndpoint;
        this.changeType = changeType;
        this.description = description;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Long getId() {
        return id;
    }

    public ApiEndpoint getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(ApiEndpoint apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}