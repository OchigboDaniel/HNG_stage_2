package com.classifyName.nameClassifier.model;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


import java.time.Instant;
import java.util.UUID;



@Data
@Entity
@Table(name = "profile_data")
public class DataEntity {
    @Id
    @Column(unique = true, name = "profile_id", nullable = false)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    private String gender;
    @Column(name = "gender_probability", nullable = false)
    private double genderProbability;
    @Column(name = "sample_size", nullable = false)
    private int sampleSize;
    private int age;
    @Column(name = "age_group", nullable = false)
    private String ageGroup;
    @Column(name = "country_id", nullable = false)
    private String countryId;
    @Column(name = "country_probability", nullable = false)
    private double countryProbability;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    public void setIAgeGroup(int age) {
        if (age >= 0 && age <= 12) {
            this.ageGroup = "child";
        } else if (age >= 13 && age <= 19) {
            this.ageGroup = "teenager";
        } else if (age >= 20 && age <= 59) {
            this.ageGroup = "adult";
        } else {
            this.ageGroup = "senior";
        }
    }

    @PrePersist
    public void onCreate() {
        if (id == null) {
            id = UuidCreator.getTimeOrderedEpoch();
        }

        if (createdAt == null) {
            createdAt = Instant.now();
        }}


}
