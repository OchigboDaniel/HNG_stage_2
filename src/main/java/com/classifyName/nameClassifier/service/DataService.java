package com.classifyName.nameClassifier.service;

import com.classifyName.nameClassifier.*;
import com.classifyName.nameClassifier.model.DataEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class DataService{

    private final RestTemplate restTemplate;
    private final IDataRepository dataRepository;

    public DataService(RestTemplate restTemplate, IDataRepository dataRepository) {

        this.restTemplate =restTemplate;
        this.dataRepository = dataRepository;
    }


    // Get Values for the ENV file
    @Value("${GENDERIZE_BASE_URL}")
    private String genderizeBaseURL;
    @Value("${AGIFY_BASE_URL}")
    private String agifyBaseURL;
    @Value("${NATIONALIZE_BASE_URL}")
    private String nationalizeBaseURL;

    //Format the Gender ApI request String URL
    public String formatAPI_URLQuery( String baseURL, String name){

        return baseURL + "?name=" + name;
    }

    public ResponseEntity<?> createProfile(RequestDTO data){
        String name = data.getName();

        //Check if the name is in the DB
        Optional<DataEntity> existing = dataRepository.findByNameIgnoreCase(name);
        if (existing.isPresent()) {
            return ResponseEntity.status(200)
                    .body(new ExistingResponseDTO(
                            "success",
                            "Profile already exists",
                            existing.get()
                    ));
        }



        // Format All URl for the name query
        String genderizeUrl = formatAPI_URLQuery(genderizeBaseURL,name);
        String agifyUrl = formatAPI_URLQuery(agifyBaseURL, name);
        String nationalizeURL = formatAPI_URLQuery(nationalizeBaseURL, name);

        //Response Mapper
        Map<String, Object> genderizeResponse;
        Map<String, Object> agifyResponse;
        Map<String, Object> nationalizeResponse;


        try {
            genderizeResponse = restTemplate.getForObject(
                    genderizeUrl,
                    Map.class
            );

            agifyResponse = restTemplate.getForObject(
                    agifyUrl,
                    Map.class
            );

            nationalizeResponse = restTemplate.getForObject(
                    nationalizeURL,
                    Map.class
            );

        } catch (ResourceAccessException ex) {
            return  ResponseEntity.status(502).body(new ErrorResponse("error", "Upstream or server failure"));
        }

        //
        EdgeCase edgeCaseAvailable = new EdgeCase();

        //If there were no resource access Exceptions, get the data Response
        //Generalizer
        String gender = (String) genderizeResponse.get("gender");
        Integer count = (Integer) genderizeResponse.get("count");
        Integer samplesNum = (Integer) genderizeResponse.get("count");
        Double gender_probability = genderizeResponse.get("probability") != null
                ? ((Number) genderizeResponse.get("probability")).doubleValue()
                : 0.0;
        // Update edge case availability
        if (gender == null || count == 0){
            edgeCaseAvailable.setEdgeCase(true);
            edgeCaseAvailable.setApiURL(genderizeBaseURL);
        }

        //Agipy
        Integer age =(Integer) agifyResponse.get("age");
        String age_grade = "";
        // Update edge case availability Agify: 0–12 → child, 13–19 → teenager, 20–59 → adult, 60+ → senior
        if (age == null){
            edgeCaseAvailable.setEdgeCase(true);
            edgeCaseAvailable.setApiURL(agifyBaseURL);
        } else if (age >= 0 && age <= 12) {
            age_grade = "child";
        } else if (age >= 13 && age <= 19) {
            age_grade = "teenager";
        } else if (age >= 20 && age <= 59) {
            age_grade = "adult";
        }else {
            age_grade = "senior";
        }


        List<Map<String, Object>> countries = (List<Map<String, Object>>) nationalizeResponse.get("country");
        //get the country with the highest propability
        Map<String, Object> country = getHighestCountry(countries);
        String country_id = (String) country.get("country_id");
        Double country_probability = country.get("probability") != null
                ? ((Number) country.get("probability")).doubleValue()
                : 0.0;;

        //Check if there are any edge case
        if (edgeCaseAvailable.isEdgeCase() == true){
            return ResponseEntity.status(502)
                    .body(new ErrorResponse("error", String.format("%s returned an invalid response", edgeCaseAvailable.getApiURL())));
        };
        
        DataEntity dataDTO = new DataEntity();
        dataDTO.setName(name);
        dataDTO.setGender(gender);
        dataDTO.setAge(age);
        dataDTO.setAge_group(age_grade);
        dataDTO.setSample_size(samplesNum);
        dataDTO.setGender_probability(gender_probability);
        dataDTO.setCountry_id(country_id);
        dataDTO.setCountry_probability(country_probability);
        dataRepository.save(dataDTO);


        return ResponseEntity.status(201)
                .body(new GenderResponseDTO("success", dataDTO));

    }

    public ResponseEntity<?> getProfileByID(UUID id){
        Optional<DataEntity> existing = dataRepository.findById(id);
        if (existing.isPresent()) {
            return ResponseEntity.status(200)
                    .body(new GenderResponseDTO(
                            "success",
                            existing.get()
                    ));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getAllProfile(String gender, String country_id, String age_group){
        List<DataEntity> profiles = dataRepository.findAll();

        List<DataEntity> filtered = profiles.stream()
                .filter(p -> gender == null || p.getGender().equalsIgnoreCase(gender))
                .filter(p -> country_id == null || p.getCountry_id().equalsIgnoreCase(country_id))
                .filter(p -> age_group == null || p.getAge_group().equalsIgnoreCase(age_group))
                .toList();

        int count = filtered.size();

        if (profiles != null && !profiles.isEmpty()){
            return ResponseEntity.status(200)
                    .body( new AllProfileResponse(
                            "success",
                            count,
                            filtered
                            )
                    );
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteById(UUID id){
        dataRepository.deleteById(id);
        return ResponseEntity.status(204)
                .build();
    }



    // function to get the country with the highest properbility
    public Map<String, Object> getHighestCountry(List<Map<String, Object>> countries) {
        return countries.stream()
                .max(Comparator.comparing(c -> (Double) c.get("probability")))
                .orElse(null);
    }
}
