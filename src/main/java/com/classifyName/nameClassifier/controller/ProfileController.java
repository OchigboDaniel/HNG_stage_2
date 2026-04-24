package com.classifyName.nameClassifier.controller;

import com.classifyName.nameClassifier.ErrorResponse;
import com.classifyName.nameClassifier.dto.RequestDTO;
import com.classifyName.nameClassifier.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS} )
@RestController
@RequestMapping("/api/profiles")
@Validated
public class ProfileController {

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
            @RequestParam(required = false) String min_age,
            @RequestParam(required = false) String max_age,
            @RequestParam(required = false) String min_country_probability,
            @RequestParam(required = false) String min_gender_probability,
            @RequestParam(name = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "1") String pageStr,
            @RequestParam(defaultValue = "10") String limitStr) {

        // sort mapping
        sortBy = switch (sortBy) {
            case "created_at" -> "createdAt";
            case "age" -> "age";
            case "country_probability" -> "countryProbability";
            case "gender_probability" -> "genderProbability";
            default -> "createdAt";
        };

        if (sortBy == null) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("error", "Invalid sort field")
            );
        }

        //safe parsing helper usage
        int page = Math.max(parseIntStr(pageStr, 10), 1) - 1; // 👈 FIX (0-based)
        int limit = Math.min(Math.max(parseIntStr(limitStr, 1), 1), 50);

        Integer minAge = parseIntStr(min_age, 1);
        Integer maxAge = parseIntStr(max_age, 120);
        Double minCountryProbability = parseDoubleStr(min_country_probability, 0.0);
        Double minGenderProbability = parseDoubleStr(min_gender_probability, 0.0);

        Sort.Direction direction =
                order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(direction, sortBy));

        return genderService.getAllProfile(
                gender,
                age_group,
                country_id,
                minAge,
                maxAge,
                minGenderProbability,
                minCountryProbability,
                pageable
        );
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


    private int parseIntStr(String value, int defaultInt) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultInt;
        }
    }



    private Double parseDoubleStr(String value, double defaultDouble) {
        try {
            return value == null ? null : Double.parseDouble(value);
        } catch (Exception e) {
            return defaultDouble;// or default like 0.0 if you prefer
        }
    }
}
