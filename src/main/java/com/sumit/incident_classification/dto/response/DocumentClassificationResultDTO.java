package com.sumit.incident_classification.dto.response;

import com.sumit.incident_classification.dto.ClassifiedTextDTO;
import com.sumit.incident_classification.entity.ClassifiedText;
import lombok.Data;

import java.util.List;

@Data
public class DocumentClassificationResultDTO {

    private Long documentId;
    List<ClassifiedTextDTO> results;

}
