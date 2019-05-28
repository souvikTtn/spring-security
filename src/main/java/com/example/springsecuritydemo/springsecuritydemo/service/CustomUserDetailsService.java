package com.example.springsecuritydemo.springsecuritydemo.service;

import com.example.springsecuritydemo.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.springsecuritydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        try {
            user=userRepository.findUsersByUserName(username);
        }
        catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }

        if(user==null){
            throw new RuntimeException("user not found");
        }
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                auths
        );
    }
}
