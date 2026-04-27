package com.classifyName.nameClassifier.repository;


import com.classifyName.nameClassifier.model.DataEntity;
import com.classifyName.nameClassifier.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}