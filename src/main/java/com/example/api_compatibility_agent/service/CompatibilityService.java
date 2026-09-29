package com.example.api_compatibility_agent.service;

import org.springframework.stereotype.Service;

import com.example.api_compatibility_agent.model.ApiChange;
import com.example.api_compatibility_agent.model.CompatibilityResult;
import com.example.api_compatibility_agent.repository.ApiChangeRepository;

@Service
public class CompatibilityService {

    private final ApiChangeRepository repository;

    public CompatibilityService(ApiChangeRepository repository) {
        this.repository = repository;
    }

    public CompatibilityResult analyze(ApiChange change) {

        String changeType = change.getChangeType();

        if ("FIELD_ADDED".equalsIgnoreCase(changeType)) {
            return new CompatibilityResult(
                    "COMPATIBLE",
                    "Adding a new field is generally backward compatible."
            );
        }

        if ("FIELD_REMOVED".equalsIgnoreCase(changeType)) {
            return new CompatibilityResult(
                    "POTENTIALLY_BREAKING",
                    "Removing a field may break existing API consumers."
            );
        }

        if ("FIELD_MODIFIED".equalsIgnoreCase(changeType)) {
            return new CompatibilityResult(
                    "POTENTIALLY_BREAKING",
                    "Modifying an existing field may break existing API consumers."
            );
        }

        return new CompatibilityResult(
                "UNKNOWN",
                "The compatibility impact of this change type is not defined yet."
        );
    }

    public CompatibilityResult analyzeById(Long id) {

        ApiChange change = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("API change not found"));

        return analyze(change);
    }
}