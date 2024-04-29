package com.example.registerApp.configuration;

import com.example.registerApp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserValidationConfig {

    @Value("${user.minAge}")
    private int minAge;

    @Bean
    public UserValidator userValidator(){
       return new UserValidator(minAge);
    }



}
