package com.classifyName.nameClassifier;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class GenderController {

    @Autowired
    GenderService genderService;

    @GetMapping("/classify")
    public ResponseEntity<?> getAPIKey(@RequestParam(required = true) @NotBlank(message = "Missing or empty name parameter") String name){
        return genderService.classifyName(name);
    }
}
