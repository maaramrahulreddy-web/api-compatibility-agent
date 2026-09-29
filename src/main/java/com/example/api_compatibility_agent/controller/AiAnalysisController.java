package com.example.api_compatibility_agent.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compatibility_agent.service.PythonAgentService;

@RestController
@RequestMapping("/api/ai")
public class AiAnalysisController {

    private final PythonAgentService pythonAgentService;

    public AiAnalysisController(PythonAgentService pythonAgentService) {
        this.pythonAgentService = pythonAgentService;
    }

    @PostMapping("/analyze")
    public String analyze(@RequestBody Map<String, String> request) {

        String change = request.get("change");

        return pythonAgentService.analyzeChange(change);
    }

    @PostMapping("/remember")
    public String remember(@RequestBody Map<String, String> request) {

        String application = request.get("application");
        String api = request.get("api");
        String field = request.get("field");

        return pythonAgentService.rememberDependency(
                application,
                api,
                field
        );
    }
}