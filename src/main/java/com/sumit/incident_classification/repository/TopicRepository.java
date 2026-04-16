package com.sumit.incident_classification.repository;

import com.sumit.incident_classification.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}
