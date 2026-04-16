package com.sumit.incident_classification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String keywords;

    @Transient
    public List<String> getKeywordList() {
        if (keywords == null) return List.of();
        return Arrays.asList(keywords.toLowerCase().split(","));
    }
}
