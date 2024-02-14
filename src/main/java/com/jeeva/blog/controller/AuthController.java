package com.jeeva.blog.controller;


import com.jeeva.blog.payload.JWTAuthResponse;
import com.jeeva.blog.payload.LoginDto;
import com.jeeva.blog.payload.RegisterDto;
import com.jeeva.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // Build Login REST API

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login (@RequestBody  LoginDto loginDto){
        String  token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register API

    @PostMapping(value = {"/register", "/signup"})
    public  ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

