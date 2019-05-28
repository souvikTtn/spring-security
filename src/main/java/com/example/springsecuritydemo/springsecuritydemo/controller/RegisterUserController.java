package com.example.springsecuritydemo.springsecuritydemo.controller;

import com.example.springsecuritydemo.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.springsecuritydemo.entity.VerificationTokenClass;
import com.example.springsecuritydemo.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.springsecuritydemo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class RegisterUserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @GetMapping("/registerUser")
    public String getRegistrationPage(){
        return "registrationForm";
    }

   /* @PostMapping("/registerUser")
    public String registerUser(User user){
        userRepository.save(user);
        return "loginForm";
    }*/


    @PostMapping("/registerUser")
    public String registerUser(User user, HttpServletRequest httpServletRequest){
        String token= UUID.randomUUID().toString();
        user.setEnabled(false);
        userRepository.save(user);
        String authUrl="http://"+httpServletRequest.getServerName()
                + ":"
                +httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath()
                +"/registrationConfirmation"
                +"?token="+token;

        System.out.println("url is "+authUrl);
        VerificationTokenClass verificationToken = new VerificationTokenClass();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return "loginForm";
    }

    @GetMapping("/registrationConfirmation")
    @ResponseBody
    public String registrationConfirmation(String token){
        VerificationTokenClass verificationToken = verificationTokenRepository.findByToken(token);
        User user =verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
      return "Confirmation Successful";
    }
}
