package com.classifyName.nameClassifier.repository;

import com.classifyName.nameClassifier.entity.RoleEnum;
import com.classifyName.nameClassifier.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
