package com.example.registerApp.service;

import com.example.registerApp.entity.UpdateUser;
import com.example.registerApp.entity.User;
import com.example.registerApp.exception.RegisterRequestException;
import com.example.registerApp.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserValidator validator;
    @Override
    public List<User> getUsersByBirthday(List<User> userList, Map<String,String> dateParam) {

        List<User> matchingUsers = new ArrayList<>();

        if(dateParam.get("from") == null || dateParam.get("to") == null){
            throw new RegisterRequestException("Param can`t be null!");
        }

        if (dateParam.get("from").isEmpty() || dateParam.get("to").isEmpty()){
            throw new RegisterRequestException("Param can`t be empty!");
        }

        LocalDate dateFrom =  LocalDate.parse(dateParam.get("from"));

        LocalDate dateTo =  LocalDate.parse(dateParam.get("to"));

        if (dateFrom.isAfter(dateTo)) {
            throw new RegisterRequestException("Date incorrect!");
        }

        for (User user : userList) {
            if (user.getBirthday().isAfter(dateFrom) && user.getBirthday().isBefore(dateTo)) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }

    @Override
    public User updateAddressAndPhone(List<User> userList, String email, UpdateUser updateUser) {
        User foundUser = userList.stream()
                .filter(u->u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(()-> new RegisterRequestException("User not found!"));

        validator.validate(foundUser);

     foundUser.setPhoneNumber(updateUser.getPhoneNumber());
     foundUser.setAddress(updateUser.getAddress());

     return foundUser;
    }


    @Override
    public User updateUser(List<User> userList, User user) {

        User userUpdate = userList.stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElseThrow(()->new RegisterRequestException("User not found!"));

        validator.validate(user);

        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setBirthday(user.getBirthday());
        userUpdate.setAddress(user.getAddress());
        userUpdate.setPhoneNumber(user.getPhoneNumber());

        return userUpdate;
    }

    @Override
    public boolean deleteUser(List<User> userList,String email) {

        boolean removed = userList.removeIf(u -> u.getEmail().equals(email));

        if (!removed) {
            throw new RegisterRequestException("User with this email not found!");
        }
            return true;

    }

        @Override
        public User createUser (User user){
            validator.validate(user);

            return User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .build();

        }


}
