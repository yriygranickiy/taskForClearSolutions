package com.example.registerApp.service;

import com.example.registerApp.entity.UpdateUser;
import com.example.registerApp.entity.User;
import com.example.registerApp.exception.RegisterRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserServiceTest {


    @Autowired
    private UserService userService;


    @Test
    void create_valid_user_test()  {

        User user =  userService.createUser(createUserEntity());

        assertNotNull(user);

        assertEquals("Nick",user.getFirstName());
        assertEquals("Svobodi 79",user.getAddress());
    }


    @Test
    void create_not_valid_email_user_test(){
        User user = User.builder()
                .firstName("Nick")
                .lastName("Bobs")
                .email("nickgmail.com.ua")
                .birthday(LocalDate.parse("2001-01-01"))
                .address("Test")
                .phoneNumber("+3049582344")
                .build();

        assertThatThrownBy(()->userService.createUser(user))
                .isInstanceOf(RegisterRequestException.class)
                .hasMessage("Invalid email format!");
    }


    @Test
    void create_not_valid_age_old_user_test(){
        User user = User.builder()
                .firstName("Nick")
                .lastName("Bobs")
                .email("nick@gmail.com.ua")
                .birthday(LocalDate.parse("2018-01-01"))
                .address("Test")
                .phoneNumber("+3049582344")
                .build();

        assertThatThrownBy(()->userService.createUser(user))
                .isInstanceOf(RegisterRequestException.class)
                .hasMessage("User must be at least 18 years old!");
    }

    @Test
    void test_update_user() {
        User existingUser = createUserEntity();

        List<User> userList=List.of(existingUser);

        User updateUser = User.builder()
                .firstName("Gim")
                .lastName("Curry")
                .email("nick@gmail.com")
                .birthday(LocalDate.parse("2001-01-25"))
                .address("Anoshkina 29")
                .phoneNumber("+0688568923")
                .build();


        User user = userService.updateUser(userList,updateUser);

        assertNotNull(user);

        assertEquals(existingUser.getAddress(), updateUser.getAddress());

        assertEquals(existingUser.getPhoneNumber(), updateUser.getPhoneNumber());

        assertEquals(existingUser.getFirstName(),updateUser.getFirstName());

        assertEquals(existingUser.getLastName(),updateUser.getLastName());
    }

    @Test
    void test_update_phone_and_address_user_test(){
        User existingUser = createUserEntity();

        List<User> listUser = List.of(existingUser);

        UpdateUser updateUser = new UpdateUser();
        updateUser.setAddress("Anoshkina 29");
        updateUser.setPhoneNumber("+0688568923");


        User user = userService.updateAddressAndPhone(listUser,existingUser.getEmail(),updateUser);

        assertNotNull(user);

        assertEquals(existingUser.getAddress(),user.getAddress());

        assertEquals(existingUser.getPhoneNumber(),user.getPhoneNumber());

    }


    @Test
    void delete_user_successful_test() {

      User deleteUser = createUserEntity();

      List<User> userList = new ArrayList<>();

      userList.add(deleteUser);

      boolean result =  userService.deleteUser(userList,deleteUser.getEmail());

      assertTrue(result);

    }

    @Test
    void delete_invalid_user_test(){
        User deleteUser = createUserEntity();

        List<User> userList = new ArrayList<>();

        userList.add(deleteUser);

        assertThatThrownBy(()->userService.deleteUser(userList,"test@gmail.com"))
                .isInstanceOf(RegisterRequestException.class)
                .hasMessage("User with this email not found!");
    }

    @Test
    void search_users_by_birth_date_range_validUser_test() {
        User user_one = createUserEntity();
        User user_two = User.builder()
                .firstName("John")
                .lastName("Sear")
                .email("sear29@gmail.com")
                .birthday(LocalDate.parse("1993-01-25"))
                .address("Svobodi 79")
                .phoneNumber("+0678567611")
                .build();

        List<User> userList = List.of(user_one,user_two);

        Map<String,String> mapParam = new HashMap<>();

        mapParam.put("from","1990-01-23");
        mapParam.put("to","2002-01-27");

        List<User> listUserRange = userService.getUsersByBirthday(userList,mapParam);


        assertEquals(2,listUserRange.size(),"Expected number of users within range");

        assertTrue(listUserRange.contains(user_one),"User one should be int the list");
        assertTrue(listUserRange.contains(user_two),"User two should not be int the list");

    }


    private User createUserEntity(){

        return User.builder()
                .firstName("Nick")
                .lastName("Lept")
                .email("nick@gmail.com")
                .birthday(LocalDate.parse("2001-01-25"))
                .address("Svobodi 79")
                .phoneNumber("+0678567611")
                .build();

    }
}