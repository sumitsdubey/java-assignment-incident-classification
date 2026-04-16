package com.sumit.incident_classification.repository;

import com.sumit.incident_classification.dto.ClassifiedTextDTO;
import com.sumit.incident_classification.entity.ClassifiedText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassifiedTextRepository extends JpaRepository<ClassifiedText, Long> {

    @Query("""
        SELECT\s
            ct.textChunk as textChunk,
            ct.assignedTopic as assignedTopic,
            ct.confidenceScore as confidenceScore
        FROM ClassifiedText ct\s
        WHERE ct.document.id = :documentId
       \s""")
    List<ClassifiedTextDTO> getDocumentResultById(@Param("documentId") Long documentId);
    long countByAssignedTopic(String topic);
    long count(); // total chunks
}