package com.sumit.incident_classification.controller;

import com.sumit.incident_classification.dto.response.ApiResponse;
import com.sumit.incident_classification.dto.response.DashboardResultDTO;
import com.sumit.incident_classification.repository.ClassifiedTextRepository;
import com.sumit.incident_classification.repository.DocumentRepository;
import com.sumit.incident_classification.repository.TopicRepository;
import com.sumit.incident_classification.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<?> getDashboard() {

        DashboardResultDTO dashboard = dashboardService.getDashboard();

        return new ResponseEntity<>(ApiResponse.success(dashboard), HttpStatus.OK);


    }
}