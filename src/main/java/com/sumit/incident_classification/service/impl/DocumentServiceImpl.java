package com.sumit.incident_classification.service.impl;

import com.sumit.incident_classification.entity.ClassifiedText;
import com.sumit.incident_classification.entity.Document;
import com.sumit.incident_classification.repository.ClassifiedTextRepository;
import com.sumit.incident_classification.repository.DocumentRepository;
import com.sumit.incident_classification.service.ClassificationService;
import com.sumit.incident_classification.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {


    private final DocumentRepository documentRepository;
    private final ClassificationService classificationService;


    @Override
    public String extractFromPdf(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is empty or missing");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        log.info("Extracting text from PDF: {}", originalFilename);

        byte[] fileBytes = file.getBytes();

        try (PDDocument pdf = Loader.loadPDF(fileBytes)) {

            if (pdf.isEncrypted()) {
                throw new IllegalArgumentException("Encrypted PDFs are not supported");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdf);

            if (text == null || text.isBlank()) {
                throw new IllegalArgumentException("No text could be extracted from the PDF. It may be a scanned image.");
            }

            log.info("Successfully extracted {} characters from PDF", text.length());
            return text.trim();
        }

    }

    public Document processDocument(String text) {

        // Save document to DB
        Document doc = new Document();
        doc.setOriginalText(text);
        doc.setCreatedAt(LocalDateTime.now());
        doc = documentRepository.save(doc);
        log.info("Document saved with ID: {}", doc.getId());

        // Split text into sentences
        List<String> sentences = classificationService.splitIntoSentences(text);
        log.info("Split into {} sentences", sentences.size());

        // Classify each sentence and save
        for (String sentence : sentences) {

            ClassificationServiceImpl.ClassificationResult result =
                    classificationService.classify(sentence);

            ClassifiedText ct = new ClassifiedText();
            ct.setDocument(doc);
            ct.setTextChunk(sentence);
            ct.setAssignedTopic(result.topic());
            ct.setConfidenceScore(result.confidence());

            classificationService.saveClassifiedText(ct);
            log.debug("Chunk classified → Topic: '{}', Confidence: {}",
                    result.topic(), result.confidence());
        }

        log.info("Document processing complete. Total chunks saved: {}", sentences.size());
        return doc;
    }



}
