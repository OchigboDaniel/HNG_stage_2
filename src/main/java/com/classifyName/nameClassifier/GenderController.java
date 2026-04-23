package com.classifyName.nameClassifier;

import com.classifyName.nameClassifier.model.DataEntity;
import com.classifyName.nameClassifier.service.DataService;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.Region;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profiles")
@Validated
public class GenderController {

    @Autowired
    DataService genderService;

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody RequestDTO data){
        return  genderService.createProfile(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable UUID id){
        return  genderService.getProfileByID(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllProfile(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String country_id,
            @RequestParam(required = false) String age_group,
            @RequestParam(required = false) Integer min_age,
            @RequestParam(required = false) Integer max_age,
            @RequestParam(required = false) Double min_country_probability,
            @RequestParam(required = false) Double min_gender_probability,
            @PageableDefault(page = 0, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "10") int limit) {

            if (limit > 50){
                limit = 50;
            }
            int page = pageable.getPageNumber();
        return genderService.getAllProfile(gender, age_group, country_id, min_age, max_age,min_gender_probability, min_country_probability, page, limit );
    }

    @GetMapping("/search")
    public ResponseEntity searchProfile(
            @RequestParam(name = "q") String keywords,
            @PageableDefault(page = 0, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "10") int limit){
        if (limit > 50){
            limit = 50;
        }
        int page = pageable.getPageNumber();
        return genderService.searchProfile(keywords, page, limit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfileById(@PathVariable UUID id){
        return genderService.deleteById(id);
    }

}
