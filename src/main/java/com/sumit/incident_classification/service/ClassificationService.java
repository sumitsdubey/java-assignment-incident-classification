package com.sumit.incident_classification.service;

import com.sumit.incident_classification.dto.response.DocumentClassificationResultDTO;
import com.sumit.incident_classification.entity.ClassifiedText;
import com.sumit.incident_classification.service.impl.ClassificationServiceImpl;

import java.util.List;

public interface ClassificationService {

    List<String> splitIntoSentences(String text);

    ClassificationServiceImpl.ClassificationResult classify(String textChunk);

    boolean isWholeWordMatch(String text, String keyword);

    DocumentClassificationResultDTO getDocumentClassificationResult(Long documentId);

    ClassifiedText saveClassifiedText(ClassifiedText classifiedText);
}
