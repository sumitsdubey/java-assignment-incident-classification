package com.sumit.incident_classification.service.impl;

import com.sumit.incident_classification.dto.response.DashboardResultDTO;
import com.sumit.incident_classification.repository.ClassifiedTextRepository;
import com.sumit.incident_classification.repository.DocumentRepository;
import com.sumit.incident_classification.repository.TopicRepository;
import com.sumit.incident_classification.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DocumentRepository documentRepository;
    private final ClassifiedTextRepository classifiedTextRepository;
    private final TopicRepository topicRepository;


    @Override
    public DashboardResultDTO getDashboard() {

        Long totalDocs = documentRepository.count();
        Long totalChunks = classifiedTextRepository.count();

        Map<String, Long> distribution = new HashMap<>();
        topicRepository.findAll().forEach(t ->
                distribution.put(t.getTitle(),
                        classifiedTextRepository.countByAssignedTopic(t.getTitle()))
        );
        distribution.put("UNCLASSIFIED",
                classifiedTextRepository.countByAssignedTopic("UNCLASSIFIED"));


        DashboardResultDTO dashboardResultDTO = new DashboardResultDTO();

        dashboardResultDTO.setTotalDocuments(totalDocs);
        dashboardResultDTO.setTotalChunks(totalChunks);
        dashboardResultDTO.setTopicDistribution(distribution);

        return dashboardResultDTO;
    }
}
