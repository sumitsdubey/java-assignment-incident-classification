package com.sumit.incident_classification.controller;

import com.sumit.incident_classification.dto.TopicDTO;
import com.sumit.incident_classification.dto.response.ApiResponse;
import com.sumit.incident_classification.entity.Topic;
import com.sumit.incident_classification.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<?> addTopics(@RequestBody List<TopicDTO> topicsRequest) {

        List<Topic> topics = topicService.createTopic(topicsRequest);
        return new ResponseEntity<>(ApiResponse.success(topics), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        return new ResponseEntity<>(ApiResponse.success(topicService.getAllTopics()), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllTopics(@PathVariable Long id) {
        return new ResponseEntity<>(ApiResponse.success(topicService.getTopicById(id)), HttpStatus.OK) ;
    }
}