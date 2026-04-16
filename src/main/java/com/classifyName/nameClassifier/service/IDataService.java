package com.classifyName.nameClassifier.service;

import com.classifyName.nameClassifier.RequestDTO;
import org.springframework.http.ResponseEntity;

public interface IDataService {
    public ResponseEntity<?> createData(RequestDTO data);
}
