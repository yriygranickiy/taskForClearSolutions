package com.example.registerApp.validator;


import com.example.registerApp.entity.User;
import com.example.registerApp.exception.RegisterRequestException;

import java.time.LocalDate;
import java.util.regex.Pattern;


public class UserValidator {

    private final String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

    private final int age;

    public UserValidator(int age) {
        this.age = age;
    }

    public void validate(User user){

        LocalDate currentDate = LocalDate.now();

        LocalDate minAgeDate = currentDate.minusYears(age);

        if (user.getBirthday().isAfter(minAgeDate)){
            throw new RegisterRequestException("User must be at least 18 years old!");
        }
        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches()){
            throw new RegisterRequestException("Invalid email format!");
        }
    }

}
