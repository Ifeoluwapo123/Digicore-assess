package com.example.digicore.service.serviceImplementation;

import com.example.digicore.DOA.AccountDao;
import com.example.digicore.dto.request.CreateAccountRequest;
import com.example.digicore.dto.request.LoginRequest;
import com.example.digicore.dto.response.CreateAccountResponse;
import com.example.digicore.dto.response.LoginResponse;
import com.example.digicore.exception.ApiBadRequestException;
import com.example.digicore.exception.ApiConflictException;
import com.example.digicore.model.Account;
import com.example.digicore.security.JwtUtils;
import com.example.digicore.security.MyUserDetailsService;
import com.example.digicore.service.AuthService;
import com.example.digicore.utils.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<CreateAccountResponse> createAccount(CreateAccountRequest accountRequest) {

        if(accountRequest.getInitialDeposit() < 500)
            throw new ApiBadRequestException("Oops cannot create account with initial deposit less than #500");

        int index = accountDao.findUserIndexByAccountName(accountRequest.getAccountName());

        if(index >= 0)
            throw new ApiConflictException("Oops account name already exist");

        //create random account number
        String accountNumber = HelperClass.generateNewAccountNumber();
        System.err.println("ACCOUNT NUMBER IS "+accountNumber);

        Account account = new Account();
        account.setAccountName(accountRequest.getAccountName());
        account.setAccountNumber(accountNumber);
        account.setPassword(encoder.encode(accountRequest.getAccountPassword()));
        account.setBalance(accountRequest.getInitialDeposit());

        accountDao.save(account);
        CreateAccountResponse res = new CreateAccountResponse();
        res.setMessage("Successfully created account");
        res.setSuccess(true);
        res.setResponseCode(201);
        res.setAccountNumber(accountNumber);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<LoginResponse> userLogin(LoginRequest loginRequest) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getAccountNumber(), loginRequest.getAccountPassword()));
        }catch (BadCredentialsException e){
            throw new ApiBadRequestException("Oops incorrect account number or password");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getAccountNumber());
        final String jwt = jwtUtils.generateToken(userDetails);

        LoginResponse res = new LoginResponse();

        Account account = new Account();
        account.setAccountNumber(loginRequest.getAccountNumber());
        account.setPassword(loginRequest.getAccountPassword());

        res.setAccessToken("204");
        res.setSuccess(true);
        res.setAccessToken(jwt);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }


}
