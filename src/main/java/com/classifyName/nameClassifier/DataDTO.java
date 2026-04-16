package com.classifyName.nameClassifier;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;



@Data
@AllArgsConstructor
public class DataDTO {
    private String name;
    private String gender;
    private double gender_probability;
    private int sample_size;
    private int age;
    private String age_group;
    private String country_id;
    private double country_probability;
    private Instant created_at;
}
