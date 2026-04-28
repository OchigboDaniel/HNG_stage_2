package com.classifyName.nameClassifier.service;

import com.classifyName.nameClassifier.dto.PaginatedData;
import com.classifyName.nameClassifier.model.DataEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ConvertToVSC {

    private String csvString;

    public void setCsvString(PaginatedData paginatedData) {

        StringBuilder sb = new StringBuilder();

        // header
        sb.append("id,name,gender,gender_probability,age,age_group,country_id,country_name,country_probability,created_at\n");

        //Get the data out of the paginated data response
        List<DataEntity> dataEntitiesList = paginatedData.getData();

        for (DataEntity p : dataEntitiesList) {
            sb.append(p.getId()).append(",");
            sb.append((p.getName())).append(",");
            sb.append((p.getGender())).append(",");
            sb.append(p.getGenderProbability()).append(",");
            sb.append(p.getAge()).append(",");
            sb.append((p.getAgeGroup())).append(",");
            sb.append((p.getCountryId())).append(",");
            sb.append((p.getCountryId())).append(",");
            sb.append(p.getCountryProbability()).append(",");
            sb.append(p.getCreatedAt()).append("\n");
        }

        this.csvString = sb.toString();
    }
}
