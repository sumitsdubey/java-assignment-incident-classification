package com.sumit.incident_classification.dto;

public interface ClassifiedTextDTO {

    String getTextChunk();
    String getAssignedTopic();
    Double getConfidenceScore();
}