package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    @Override
    public UserDto creatUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        log.info("userEntity ={}", userEntity);
        log.info("userDto ={}", userDto);
        userRepository.save(userEntity);
        UserDto result = mapper.map(userEntity, UserDto.class);
        return result;
    }


    //유저 개별조회
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new RuntimeException("존재하지 않는 유저입니다");
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);
        return userDto;
    }


    //유저 전체 조회
    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
