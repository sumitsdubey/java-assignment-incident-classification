package com.sumit.incident_classification.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardResultDTO {

    private Long totalDocuments;
    private Long totalChunks;
    private Map<String, Long> topicDistribution;
}
