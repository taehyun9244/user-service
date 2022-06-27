package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
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

@RestController
@RequestMapping("/")
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

    @GetMapping("/heath_check")
    public String status(){
        return "Working User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

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

}
