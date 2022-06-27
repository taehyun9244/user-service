package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;

public interface UserService {
    UserDto creatUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
