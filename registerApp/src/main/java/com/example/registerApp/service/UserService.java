package com.example.registerApp.service;

import com.example.registerApp.entity.UpdateUser;
import com.example.registerApp.entity.User;

import java.util.List;
import java.util.Map;


public interface UserService {

    List<User> getUsersByBirthday(List<User> userList, Map<String,String> dateParam);

    User updateAddressAndPhone(List<User> userList,String email, UpdateUser updateUser);

    User updateUser(List<User> userList,User user);

    boolean deleteUser(List<User> userList,String email);

    User createUser(User user);
}
