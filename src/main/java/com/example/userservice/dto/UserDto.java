package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Data creatAt;
    private String address;
    private String phoneNum;

    private String encryptedPwd;

}
