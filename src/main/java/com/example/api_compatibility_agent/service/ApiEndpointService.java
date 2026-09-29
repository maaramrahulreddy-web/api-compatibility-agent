package com.example.api_compatibility_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api_compatibility_agent.model.ApiEndpoint;
import com.example.api_compatibility_agent.repository.ApiEndpointRepository;

@Service
public class ApiEndpointService {

    private final ApiEndpointRepository repository;

    public ApiEndpointService(ApiEndpointRepository repository) {
        this.repository = repository;
    }

    public ApiEndpoint createEndpoint(ApiEndpoint endpoint) {
        return repository.save(endpoint);
    }

    public List<ApiEndpoint> getAllEndpoints() {
        return repository.findAll();
    }
}