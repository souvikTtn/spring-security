package com.example.springsecuritydemo.springsecuritydemo.repository;

import com.example.springsecuritydemo.springsecuritydemo.entity.VerificationTokenClass;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationTokenClass,Integer> {
    VerificationTokenClass findByToken(String token);
}
