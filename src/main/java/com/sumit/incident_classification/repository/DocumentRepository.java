package com.sumit.incident_classification.repository;


import com.sumit.incident_classification.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DocumentRepository extends JpaRepository<Document, Long> {}


