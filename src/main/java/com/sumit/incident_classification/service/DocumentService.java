package com.sumit.incident_classification.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {


    String extractFromPdf(MultipartFile file) throws IOException;




}
