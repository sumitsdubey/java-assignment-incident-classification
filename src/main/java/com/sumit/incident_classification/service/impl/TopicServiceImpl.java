package com.sumit.incident_classification.service.impl;

import com.sumit.incident_classification.dto.TopicDTO;
import com.sumit.incident_classification.entity.Topic;
import com.sumit.incident_classification.repository.TopicRepository;
import com.sumit.incident_classification.service.TopicService;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public List<Topic> createTopic(List<TopicDTO> topicDTO) {

        return topicDTO.stream().map(dto -> {
            Topic t = new Topic();
            t.setTitle(dto.getTitle());
            t.setKeywords(String.join(",", dto.getKeywords()));

            return topicRepository.save(t);
        }).toList();
    }


    @Override
    public Topic getTopicById(Long id) {

        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
    }

    @Override
    public List<Topic> getAllTopics() {

        return topicRepository.findAll();
    }
}
