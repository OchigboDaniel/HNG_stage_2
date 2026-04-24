package com.classifyName.nameClassifier;

import com.classifyName.nameClassifier.dto.DataDTO;
import com.classifyName.nameClassifier.model.DataEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

@Component
@RequiredArgsConstructor
public class SeedConfig {
    private final  IDataRepository profileRepo;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void seedData() {

        //  prevent duplicate seeding
        if (profileRepo.count() > 0) {
            return;
        }


        List<DataEntity> profiles = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            profiles.add(toEntity(generateDTO(i)));
        }

        profileRepo.saveAll(profiles);


    }

    private DataEntity toEntity(DataDTO dto) {

        DataEntity p = new DataEntity();

        p.setName(dto.getName());
        p.setGender(dto.getGender());

        p.setAge(dto.getAge());
        p.setCountryId(dto.getCountry_id());

        p.setGenderProbability(dto.getGender_probability());
        p.setCountryProbability(dto.getCountry_probability());

        p.setCreatedAt(dto.getCreated_at());

        p.setAgeGroup(dto.getAge());

        return p;
    }
        private DataDTO generateDTO(int i) {

            Random random = new Random();

            DataDTO dto = new DataDTO();

            dto.setName("user_" + i);

            dto.setGender(i % 2 == 0 ? "male" : "female");

            int age = 18 + random.nextInt(45);
            dto.setAge(age);

            dto.setAge_group(generateAgeGroup(age));

            dto.setCountry_id("EU");

            dto.setGender_probability(round(0.70 + random.nextDouble() * 0.30));

            dto.setCountry_probability(round(0.60 + random.nextDouble() * 0.40));

            dto.setSample_size(100 + random.nextInt(900));

            dto.setCreated_at(Instant.now().minusSeconds(random.nextInt(100000)));

            return dto;
        }

        private String generateAgeGroup(int age){
            if(age >= 1 && age <= 12){
                return "young";
            } else if (age >= 13 && age <= 19) {
                return "teenager";

            } else if (age >= 20 && age <= 40) {
                return "adult";
            }
            else {
                return "senior";
        }
        }

}
