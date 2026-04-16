package com.classifyName.nameClassifier;


import com.classifyName.nameClassifier.model.DataEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExistingResponseDTO {
    private String status;
    private String message;
    private DataEntity data;
}
