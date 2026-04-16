package com.classifyName.nameClassifier;

import com.classifyName.nameClassifier.model.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public class AllProfileResponse {
    private String status;
    private int count;
    private List<DataEntity> profileList;

}
