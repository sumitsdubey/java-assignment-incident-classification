package com.sumit.incident_classification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifiedText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @Column(columnDefinition = "TEXT")
    private String textChunk;

    private String assignedTopic;

    private Double confidenceScore;
}
