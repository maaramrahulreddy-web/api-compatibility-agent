package com.example.api_compatibility_agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_compatibility_agent.model.ApiEndpoint;

public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {

}