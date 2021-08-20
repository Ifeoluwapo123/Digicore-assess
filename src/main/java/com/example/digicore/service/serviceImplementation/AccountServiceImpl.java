package com.example.digicore.service.serviceImplementation;

import com.example.digicore.DOA.AccountDao;
import com.example.digicore.DOA.AccountHistoryDoa;
import com.example.digicore.dto.request.DepositRequest;
import com.example.digicore.dto.request.WithdrawRequest;
import com.example.digicore.dto.response.AccountInfoResponse;
import com.example.digicore.dto.response.AccountResponse;
import com.example.digicore.dto.response.AcctHistoryResponse;
import com.example.digicore.dto.response.Response;
import com.example.digicore.exception.ApiBadRequestException;
import com.example.digicore.exception.ApiResourceNotFoundException;
import com.example.digicore.model.Account;
import com.example.digicore.model.AccountHistory;
import com.example.digicore.security.JwtUtils;
import com.example.digicore.security.MyUserDetailsService;
import com.example.digicore.service.AccountService;
import com.example.digicore.utils.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final AccountHistoryDoa accountHistoryDoa;
    private final Double maximumDeposit = 1000000.0;
    private final Double minimumDeposit = 1.0;
    private final Double minimumBalance = 500.0;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao,  AccountHistoryDoa accountHistoryDoa) {
        this.accountDao = accountDao;
        this.accountHistoryDoa = accountHistoryDoa;
    }

    public ResponseEntity<AccountInfoResponse> getAccountInformation(String accountNumber){
        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        AccountResponse accountResponse = new AccountResponse();

        int index = accountDao.findUserIndexByAccountNumber(null, accountNumber);

        if(index >= 0) {
            Account account = accountDao.find(index).get();
            accountResponse.setAccountName(account.getAccountName());
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setBalance(account.getBalance());

            accountInfoResponse.setAccountResponse(accountResponse);
        }else{
            throw new ApiResourceNotFoundException("Oops account not found !!!");
        }

        accountInfoResponse.setResponseCode(200);
        accountInfoResponse.setSuccess(true);
        accountInfoResponse.setMessage("Successfully fetched account details");

        return new ResponseEntity<>(accountInfoResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<List<AcctHistoryResponse>> getAccountStatement(String accountNumber){
        return new ResponseEntity<>(accountHistoryDoa.getAccountHistory(accountNumber), HttpStatus.OK);
    }

    public ResponseEntity<Response> withdraw(WithdrawRequest withdrawRequest){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(withdrawRequest.getAccountNumber(), withdrawRequest.getAccountPassword()));
        }catch (BadCredentialsException e){
            throw new ApiBadRequestException("Oops incorrect account number or password");
        }

        if(withdrawRequest.getWithdrawnAmount() < 1.0) throw new ApiBadRequestException("Oops invalid amount");

        Response res = new Response();
        Date currentDate = HelperClass.generateCurrentDateTime();

        int index = accountDao.findUserIndexByAccountNumber(null, withdrawRequest.getAccountNumber());

        if(index >= 0) {
            Account account = accountDao.find(index).get();
            Double currentBalance = account.getBalance() - withdrawRequest.getWithdrawnAmount();
            if(currentBalance < minimumBalance) throw new ApiBadRequestException("Oops cannot withdraw below #500.00");
            //set account balance
            account.setBalance(currentBalance);
            accountDao.save(account);

            //set account history
            AccountHistory accountHistory = new AccountHistory();
            accountHistory.setAccountNumber(withdrawRequest.getAccountNumber());
            accountHistory.setAmount(withdrawRequest.getWithdrawnAmount());
            accountHistory.setAccountBalance(currentBalance);
            accountHistory.setNarration(withdrawRequest.getNarration());
            accountHistory.setTransactionDate(currentDate);
            accountHistory.setTransactionType("Withdrawal");
            accountHistoryDoa.save(accountHistory);

        }else{
            throw new ApiResourceNotFoundException("Oops account not found !!!");
        }

        res.setMessage("Successfully withdrawn amount");
        res.setSuccess(true);
        res.setResponseCode(201);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<Response> deposit(DepositRequest depositRequest){
        Response res = new Response();
        Date currentDate = HelperClass.generateCurrentDateTime();

        if(depositRequest.getAmount() >= maximumDeposit || depositRequest.getAmount() <= minimumDeposit)
            throw new ApiBadRequestException("Oops invalid amount to deposit");

        //find account by account number
        int index = accountDao.findUserIndexByAccountNumber(null, depositRequest.getAccountNumber());

        if(index >= 0) {
            Account account = accountDao.find(index).get();

            //set account history
            AccountHistory accountHistory = new AccountHistory();
            accountHistory.setAccountNumber(depositRequest.getAccountNumber());
            accountHistory.setAmount(depositRequest.getAmount());
            accountHistory.setAccountBalance(account.getBalance() + depositRequest.getAmount());
            accountHistory.setNarration(depositRequest.getNarration());
            accountHistory.setTransactionDate(currentDate);
            accountHistory.setTransactionType("Deposit");
            accountHistoryDoa.save(accountHistory);

            //set account balance
            account.setBalance(account.getBalance() + depositRequest.getAmount());
            accountDao.save(account);

        }else{
            throw new ApiResourceNotFoundException("Oops account not found !!!");
        }

        res.setMessage("Successfully deposited amount");
        res.setSuccess(true);
        res.setResponseCode(201);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
