package com.example.digicore.controller;

import com.example.digicore.dto.request.DepositRequest;
import com.example.digicore.dto.request.WithdrawRequest;
import com.example.digicore.dto.response.AccountInfoResponse;
import com.example.digicore.dto.response.AcctHistoryResponse;
import com.example.digicore.dto.response.Response;
import com.example.digicore.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path="/account_info/{accountNumber}")
    public ResponseEntity<AccountInfoResponse> getAccountInformation(@PathVariable String accountNumber){
        return accountService.getAccountInformation(accountNumber);
    }

    @GetMapping(path="/account_statement/{accountNumber}")
    public ResponseEntity<List<AcctHistoryResponse>> getAccountStatement(@PathVariable String accountNumber){
        return accountService.getAccountStatement(accountNumber);
    }

    @PostMapping(path="/withdrawal")
    public ResponseEntity<Response> withdraw(@RequestBody WithdrawRequest withdrawReq){
        return accountService.withdraw(withdrawReq);
    }

    @PostMapping(path="/deposit")
    public ResponseEntity<Response> deposit(@RequestBody DepositRequest depositReq){
        return accountService.deposit(depositReq);
    }
}
