package com.classifyName.nameClassifier.controller;


import com.classifyName.nameClassifier.model.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExistingResponseDTO {
    private String status;
    private String message;
    private DataEntity data;
}
