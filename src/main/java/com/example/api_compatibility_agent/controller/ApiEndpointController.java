package com.example.api_compatibility_agent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compatibility_agent.model.ApiEndpoint;
import com.example.api_compatibility_agent.service.ApiEndpointService;

@RestController
@RequestMapping("/api/endpoints")
public class ApiEndpointController {

    private final ApiEndpointService service;

    public ApiEndpointController(ApiEndpointService service) {
        this.service = service;
    }

    @PostMapping
    public ApiEndpoint createEndpoint(@RequestBody ApiEndpoint endpoint) {
        return service.createEndpoint(endpoint);
    }

    @GetMapping
    public List<ApiEndpoint> getAllEndpoints() {
        return service.getAllEndpoints();
    }
}