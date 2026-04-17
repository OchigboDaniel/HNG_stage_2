package com.classifyName.nameClassifier;

import com.classifyName.nameClassifier.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IDataRepository extends JpaRepository<DataEntity, UUID> {
    Optional<DataEntity> findByName(String name);

}
