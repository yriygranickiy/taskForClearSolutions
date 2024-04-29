package com.example.registerApp.controller;


import com.example.registerApp.entity.UpdateUser;
import com.example.registerApp.entity.User;
import com.example.registerApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final List<User> userList = new ArrayList<>();


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user){

        userList.add(userService.createUser(user));

        return  ResponseEntity.ok("User created successfully!");
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email,
                                        @RequestBody User updateUser){

        userService.updateUser(userList,updateUser);

        return  ResponseEntity.ok("User updated successfully!");

    }

    @PatchMapping("/{email}")
    public ResponseEntity<?> updateAdressAndPhoneUser(@PathVariable String email, @RequestBody
    UpdateUser updateUser){

        userService.updateAddressAndPhone(userList,email, updateUser);

        return ResponseEntity.ok("User update successfully!");

    }



    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {

        userService.deleteUser(userList,email);

        return ResponseEntity.ok("User deleted successfully!");
    }





    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByBirthDateRange(
            @RequestParam Map<String,String> dateParam) {

        List<User> resultList =  userService.getUsersByBirthday(userList,dateParam);

        return ResponseEntity.ok(resultList);
    }



}
