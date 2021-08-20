package com.example.digicore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String accountNumber;
    private String accountName;
    private String password;
    private Double balance;

    public Account(Long id) {
        this.id = id;
    }

    public Account(String accountName) {
        this.accountName = accountName;
    }

    public Account(Long id, String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
