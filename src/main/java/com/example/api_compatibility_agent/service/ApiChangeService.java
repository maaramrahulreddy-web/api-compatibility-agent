package com.example.api_compatibility_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api_compatibility_agent.model.ApiChange;
import com.example.api_compatibility_agent.repository.ApiChangeRepository;

@Service
public class ApiChangeService {

    private final ApiChangeRepository repository;

    public ApiChangeService(ApiChangeRepository repository) {
        this.repository = repository;
    }

    public ApiChange createChange(ApiChange change) {
        return repository.save(change);
    }

    public List<ApiChange> getAllChanges() {
        return repository.findAll();
    }
}