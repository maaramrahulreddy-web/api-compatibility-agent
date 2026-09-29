package com.example.api_compatibility_agent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compatibility_agent.model.ApiChange;
import com.example.api_compatibility_agent.model.CompatibilityResult;
import com.example.api_compatibility_agent.service.CompatibilityService;

@RestController
@RequestMapping("/api/compatibility")
public class CompatibilityController {

    private final CompatibilityService service;

    public CompatibilityController(CompatibilityService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public CompatibilityResult analyze(@RequestBody ApiChange change) {
        return service.analyze(change);
    }

    @GetMapping("/analyze/{id}")
    public CompatibilityResult analyzeById(@PathVariable Long id) {
        return service.analyzeById(id);
    }
}