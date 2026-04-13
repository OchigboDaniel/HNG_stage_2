package com.classifyName.nameClassifier;


import lombok.Data;

@Data
public class GenderResponseDTO {
    private String status;
    private DataResponseDTO data;

    public GenderResponseDTO(String status, DataResponseDTO data) {
        this.status = status;
        this.data = data;
    }
}
