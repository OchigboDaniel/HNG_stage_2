package com.classifyName.nameClassifier.dto;

import com.classifyName.nameClassifier.model.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedData<T> {
    private String status;
    private int page;
    private int limit;
    private int total;
    private List<DataEntity> data;
}
