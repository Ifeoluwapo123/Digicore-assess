package com.example.digicore.controller;

import com.example.digicore.dto.request.CreateAccountRequest;
import com.example.digicore.dto.request.LoginRequest;
import com.example.digicore.dto.response.CreateAccountResponse;
import com.example.digicore.dto.response.LoginResponse;
import com.example.digicore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/testing")
    public String testing(){
        return "Hello world";
    }

    @PostMapping(path="/create_account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest accountReq) throws Exception {
       return authService.createAccount(accountReq);
    }

    @PostMapping(path="/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginReq) throws Exception {
        return authService.userLogin(loginReq);
    }
}
