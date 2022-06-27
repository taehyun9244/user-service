package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity{

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/users/**").permitAll();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
