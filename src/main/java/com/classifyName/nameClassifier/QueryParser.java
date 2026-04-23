package com.classifyName.nameClassifier;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class QueryParser {

    public SearchFilter parse(String q) {

        String keyword = q.toLowerCase().trim();

        SearchFilter filter = new SearchFilter();

        List<String> genders = new ArrayList<>();

        if (keyword.matches(".*\\bfemale(s)?\\b.*")) genders.add("female");
        if (keyword.matches(".*\\bmale(s)?\\b.*")) genders.add("male");

        filter.setGenders(genders.isEmpty() ? null : genders);

        if (keyword.contains("teenager")) filter.setAgeGroup("teenager");
        if (keyword.contains("adult")) filter.setAgeGroup("adult");
        if (keyword.contains("young")) filter.setAgeGroup("young");

        Matcher matcher = Pattern.compile("\\d+").matcher(keyword);

        if (keyword.contains("above") && matcher.find()) {
            filter.setMinAge(Integer.parseInt(matcher.group()));
        }

        if (keyword.contains("below") && matcher.find()) {
            filter.setMaxAge(Integer.parseInt(matcher.group()));
        }

        return filter;
    }

}
