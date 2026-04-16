package com.sumit.incident_classification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class TopicDTO {
    @NotBlank(message = "Title can't be blank")
    private String title;
    @NotEmpty(message = "Keywords can't be empty")
    private List<String> keywords;
}
