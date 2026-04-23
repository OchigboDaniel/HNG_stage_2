package com.classifyName.nameClassifier.dto;


import com.classifyName.nameClassifier.model.DataEntity;
import lombok.Data;

@Data
public class ProfileResponseDTO {
    private String status;
    private DataEntity data;

    public ProfileResponseDTO(String status, DataEntity data) {
        this.status = status;
        this.data = data;
    }
}
