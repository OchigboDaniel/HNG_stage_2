package com.classifyName.nameClassifier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


import java.time.Instant;
import java.util.UUID;



@Data
@Entity
@Table(name = "data_entity")
public class DataEntity {
    @Id
    @UuidGenerator
    private UUID id;
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
