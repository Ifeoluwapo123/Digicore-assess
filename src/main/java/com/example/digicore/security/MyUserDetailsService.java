package com.example.digicore.security;

import com.example.digicore.DOA.AccountDao;
import com.example.digicore.exception.ApiBadRequestException;
import com.example.digicore.exception.ApiResourceNotFoundException;
import com.example.digicore.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) {
        int index = accountDao.findUserIndexByAccountNumber(null, accountNumber);

        if(index < 0) throw new ApiResourceNotFoundException("Oops account not found !!!");

        Optional<Account> account = accountDao.find(index);

        return org.springframework.security.core.userdetails.User
                .withUsername(account.get().getAccountNumber())
                .password(account.get().getPassword())
                .authorities(new ArrayList<>())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
