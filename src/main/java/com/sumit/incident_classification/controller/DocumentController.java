package com.sumit.incident_classification.controller;

import com.sumit.incident_classification.dto.response.ApiResponse;
import com.sumit.incident_classification.dto.response.DocumentClassificationResultDTO;
import com.sumit.incident_classification.entity.ClassifiedText;
import com.sumit.incident_classification.entity.Document;
import com.sumit.incident_classification.repository.ClassifiedTextRepository;
import com.sumit.incident_classification.service.ClassificationService;
import com.sumit.incident_classification.service.impl.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final ClassificationService  classificationService;

    // Upload PDF
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException, IOException {
        String text = documentService.extractFromPdf(file);
        Document doc = documentService.processDocument(text);
        return new ResponseEntity<>(ApiResponse.success(doc), HttpStatus.CREATED);
    }

    // Upload raw text
    @PostMapping("/text")
    public ResponseEntity<?> uploadText(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().body("Text cannot be empty");
        }
        Document doc = documentService.processDocument(text);
        return new ResponseEntity<>(ApiResponse.success(doc), HttpStatus.CREATED);
    }

    // Get results for a document
    @GetMapping("/{id}/results")
    public ResponseEntity<?> getResults(@PathVariable Long id) {

        DocumentClassificationResultDTO results = classificationService.getDocumentClassificationResult(id);

        return ResponseEntity.ok(ApiResponse.success(results));
    }
}
