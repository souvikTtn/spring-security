package com.example.springsecuritydemo.springsecuritydemo.repository;

import com.example.springsecuritydemo.springsecuritydemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findUsersByUserName(String userName);
}
