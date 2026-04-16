package com.classifyName.nameClassifier;


import com.classifyName.nameClassifier.model.DataEntity;
import lombok.Data;

@Data
public class GenderResponseDTO {
    private String status;
    private DataEntity data;

    public GenderResponseDTO(String status, DataEntity data) {
        this.status = status;
        this.data = data;
    }
}
