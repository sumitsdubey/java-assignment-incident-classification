package com.sumit.incident_classification.service.impl;

import com.sumit.incident_classification.dto.ClassifiedTextDTO;
import com.sumit.incident_classification.dto.response.DocumentClassificationResultDTO;
import com.sumit.incident_classification.entity.ClassifiedText;
import com.sumit.incident_classification.entity.Topic;
import com.sumit.incident_classification.repository.ClassifiedTextRepository;
import com.sumit.incident_classification.repository.TopicRepository;
import com.sumit.incident_classification.service.ClassificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationServiceImpl implements ClassificationService {

    private final TopicRepository topicRepository;
    private final ClassifiedTextRepository classifiedTextRepository;

    // Result Record
    public record ClassificationResult(
            String topic,
            double confidence,
            int matchedKeywords,
            int totalKeywords
    ) {}

    // Split text into sentences
    @Override
    public List<String> splitIntoSentences(String text) {

        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        String[] parts = text.split("(?<=[.!?])\\s+");

        return Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.split("\\s+").length >= 3)
                .collect(Collectors.toList());
    }


    // Classify a single text chunk
    @Override
    public ClassificationResult classify(String textChunk) {

        if (textChunk == null || textChunk.isBlank()) {
            return new ClassificationResult("UNCLASSIFIED", 0.0, 0, 0);
        }

        List<Topic> topics = topicRepository.findAll();

        if (topics.isEmpty()) {
            log.warn("No topics found in database. Everything will be UNCLASSIFIED.");
            return new ClassificationResult("UNCLASSIFIED", 0.0, 0, 0);
        }

        String lowerChunk = textChunk.toLowerCase();

        String bestTopic = "UNCLASSIFIED";
        int bestScore = 0;
        int bestTotalKeywords = 0;
        int bestMatchedKeywords = 0;

        // Score each topic
        for (Topic topic : topics) {

            List<String> keywords = topic.getKeywordList();
            if (keywords.isEmpty()) continue;

            int score = 0;
            int matchCount = 0;

            for (String keyword : keywords) {
                String kw = keyword.trim().toLowerCase();
                if (kw.isEmpty()) continue;

                if (lowerChunk.contains(kw)) {
                    if (isWholeWordMatch(lowerChunk, kw)) {
                        score += 2; // whole word match → 2 points
                    } else {
                        score += 1; // partial match → 1 point
                    }
                    matchCount++;
                }
            }

            log.debug("Topic: '{}' | Score: {} | Matched: {}/{}",
                    topic.getTitle(), score, matchCount, keywords.size());

            if (score > bestScore || (score == bestScore && matchCount > bestMatchedKeywords)) {
                bestScore = score;
                bestTopic = topic.getTitle();
                bestTotalKeywords = keywords.size();
                bestMatchedKeywords = matchCount;
            }
        }

        double confidence = 0.0;
        if (!bestTopic.equals("UNCLASSIFIED") && bestTotalKeywords > 0) {
            // Confidence = matched keywords / total keywords of winning topic
            confidence = Math.min(1.0, (double) bestMatchedKeywords / bestTotalKeywords);
            // Round to 2 decimal places
            confidence = Math.round(confidence * 100.0) / 100.0;
        }

        log.info("Classified chunk → Topic: '{}' | Confidence: {} | Matched: {}/{}",
                bestTopic, confidence, bestMatchedKeywords, bestTotalKeywords);

        return new ClassificationResult(bestTopic, confidence, bestMatchedKeywords, bestTotalKeywords);
    }


    @Override
    public boolean isWholeWordMatch(String text, String keyword) {
        // Use word boundary regex
        String pattern = "\\b" + java.util.regex.Pattern.quote(keyword) + "\\b";
        return text.matches(".*" + pattern + ".*");
    }

    @Override
    public DocumentClassificationResultDTO getDocumentClassificationResult(Long documentId) {

        List<ClassifiedTextDTO> result = classifiedTextRepository.getDocumentResultById(documentId);

        DocumentClassificationResultDTO resultDTO = new DocumentClassificationResultDTO();
        resultDTO.setDocumentId(documentId);
        resultDTO.setResults(result);

        return resultDTO;
    }

    @Override
    public ClassifiedText saveClassifiedText(ClassifiedText classifiedText) {
        return classifiedTextRepository.save(classifiedText);
    }


}