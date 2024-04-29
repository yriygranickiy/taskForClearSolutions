package com.example.registerApp.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUser {


    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;
}
