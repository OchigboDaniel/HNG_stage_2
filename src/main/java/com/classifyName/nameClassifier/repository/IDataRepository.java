package com.classifyName.nameClassifier.repository;

import com.classifyName.nameClassifier.model.DataEntity;
import com.classifyName.nameClassifier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IDataRepository extends JpaRepository<DataEntity, UUID> {
    Optional<DataEntity> findByName(String name);

    @Query("SELECT p FROM DataEntity p WHERE " +
            "(:genders IS NULL OR LOWER(p.gender) IN :genders) AND " +
            "(:ageGroup IS NULL OR p.ageGroup = :ageGroup) AND " +
            "(:countryId IS NULL OR p.countryId = :countryId) AND " +
            "(:minAge IS NULL OR p.age >= :minAge) AND " +
            "(:maxAge IS NULL OR p.age <= :maxAge)")
    List<DataEntity> searchProfile(
            @Param("genders") List<String> genders,
            @Param("ageGroup") String ageGroup,
            @Param("countryId") String countryId,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge
    );



}
