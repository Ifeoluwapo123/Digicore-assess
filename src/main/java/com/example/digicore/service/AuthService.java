package com.example.digicore.service;

import com.example.digicore.dto.request.CreateAccountRequest;
import com.example.digicore.dto.request.LoginRequest;
import com.example.digicore.dto.response.CreateAccountResponse;
import com.example.digicore.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<CreateAccountResponse> createAccount(CreateAccountRequest accountRequest) throws Exception;
    ResponseEntity<LoginResponse> userLogin(LoginRequest loginRequest) throws Exception;
}
