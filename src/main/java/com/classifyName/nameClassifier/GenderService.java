package com.classifyName.nameClassifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class GenderService {

    private final RestTemplate restTemplate;

    public GenderService(RestTemplate restTemplate) {
        this.restTemplate =restTemplate;
    }


    // Get Values for the ENV file
    @Value("${BASE_URL}")
    private String baseURL;
    @Value("${GENDER_API_KEY}")
    private String apiKey;

    //Format the Gender ApI request String URL
    public String formatGenderAPI_URL( String name){

        return baseURL + "?name=" + name;
    }

    public ResponseEntity<?> classifyName(String name){

        String genderUrl = formatGenderAPI_URL(name);

        Map<String, Object> response;
        try {
            response = restTemplate.getForObject(
                    genderUrl,
                    Map.class
            );
        } catch (ResourceAccessException ex) {
            return  ResponseEntity.status(502).body(new ErrorResponse("error", "Upstream or server failure"));
        }

        if (response == null){
            ResponseEntity.unprocessableContent()
                    .body(new ErrorResponse("error", "unable to Process request"));
        }

        String gender = (String) response.get("gender");
        int count = (Integer) response.get("count");

        if(gender == null || count == 0){
            return ResponseEntity.unprocessableContent().body(new ErrorResponse("error", "No prediction available for the provided name"));
        }


        int samplesNum = (Integer) response.get("count");
        double probability = (Double) response.get("probability");
        boolean is_confident = probability >= 0.7 && samplesNum >= 100;


        return ResponseEntity.ok().body(new GenderResponseDTO("success",
                new DataResponseDTO(
                        name,
                        gender,
                        probability,
                        samplesNum,
                        is_confident,
                        Instant.now())));
    }
}
