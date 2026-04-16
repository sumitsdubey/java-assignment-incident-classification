package com.sumit.incident_classification.service;


import com.sumit.incident_classification.dto.TopicDTO;
import com.sumit.incident_classification.entity.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> createTopic(List<TopicDTO> topic);
    Topic getTopicById(Long id);
    List<Topic> getAllTopics();

}
