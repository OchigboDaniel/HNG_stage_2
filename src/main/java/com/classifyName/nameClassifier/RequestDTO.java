package com.classifyName.nameClassifier;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class RequestDTO {
    @NotBlank
    private String name;
}
