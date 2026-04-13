package com.classifyName.nameClassifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DataResponseDTO {
    private String name;
    private String gender;
    private double probability;
    private int sample_size;
    private boolean is_confident;
    private Instant processed_at;
}