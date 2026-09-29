package com.example.api_compatibility_agent.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compatibility_agent.model.ApiChange;
import com.example.api_compatibility_agent.service.ApiChangeService;

@RestController
@RequestMapping("/api/changes")
public class ApiChangeController {

    private final ApiChangeService service;

    public ApiChangeController(ApiChangeService service) {
        this.service = service;
    }

    @PostMapping
    public ApiChange createChange(@RequestBody ApiChange change) {
        return service.createChange(change);
    }

    @GetMapping
    public List<ApiChange> getAllChanges() {
        return service.getAllChanges();
    }
}