package com.example.api_compatibility_agent.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PythonAgentService {

    private final RestClient restClient;

    public PythonAgentService() {

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:5000")
                .build();
    }

    public String analyzeChange(String change) {

        Map<String, String> request = Map.of(
                "change", change
        );

        return restClient.post()
                .uri("/analyze")
                .body(request)
                .retrieve()
                .body(String.class);
    }

    public String rememberDependency(String application, String api, String field) {

        Map<String, String> request = Map.of(
                "api_name", api,
                "consumer", application,
                "field", field
        );

        return restClient.post()
                .uri("/remember")
                .body(request)
                .retrieve()
                .body(String.class);
    }
}