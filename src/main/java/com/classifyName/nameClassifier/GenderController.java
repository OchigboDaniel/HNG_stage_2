package com.classifyName.nameClassifier;

import com.classifyName.nameClassifier.service.DataService;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.Region;
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
            @RequestParam(required = false) String age_group)
    {
        return genderService.getAllProfile(gender, country_id, age_group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfileById(@PathVariable UUID id){
        return genderService.deleteById(id);
    }

}
