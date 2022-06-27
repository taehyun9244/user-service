package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;
    private Greeting greeting;

    @Autowired
    public UserController(Environment env, UserService userService, Greeting greeting) {
        this.env = env;
        this.userService = userService;
        this.greeting = greeting;
    }

    //작동 체크
    @GetMapping("/health_check")
    public String status(){
        return String.format("Working User Service on PORT %s", env.getProperty("local.server.port"));
    }

    //테스트
    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

    //회원가입
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> creatUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);

        log.info("userDtp={}", userDto);
        userService.creatUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return  ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    //유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> reuslt = new ArrayList<>();
        userList.forEach(v -> {
            reuslt.add(new ModelMapper().map(v, ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(reuslt);
    }

    //유저 개별조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser reuslt = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(reuslt);
    }

}
