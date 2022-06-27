package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.util.List;

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

    private List<ResponseOrder> orders;

}
