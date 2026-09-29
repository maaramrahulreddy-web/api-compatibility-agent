package com.example.api_compatibility_agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_compatibility_agent.model.ApiChange;

public interface ApiChangeRepository extends JpaRepository<ApiChange, Long> {

}