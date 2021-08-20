package com.example.digicore.service;

import com.example.digicore.dto.request.DepositRequest;
import com.example.digicore.dto.request.WithdrawRequest;
import com.example.digicore.dto.response.AccountInfoResponse;
import com.example.digicore.dto.response.AcctHistoryResponse;
import com.example.digicore.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    ResponseEntity<AccountInfoResponse> getAccountInformation(String accountNumber);
    ResponseEntity<List<AcctHistoryResponse>> getAccountStatement(String accountNumber);
    ResponseEntity<Response> withdraw(WithdrawRequest withdrawRequest);
    ResponseEntity<Response> deposit(DepositRequest depositRequest);
}
