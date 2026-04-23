package com.classifyName.nameClassifier;

import lombok.Data;

import java.util.List;

@Data
public class SearchFilter {

        private List<String> genders;
        private String ageGroup;
        private String countryId;
        private Integer minAge;
        private Integer maxAge;

}
