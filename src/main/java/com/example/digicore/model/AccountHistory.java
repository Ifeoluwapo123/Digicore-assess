package com.example.digicore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistory {
    private String accountNumber;
    private Date transactionDate;
    private String transactionType;
    private String narration;
    private Double amount;
    private Double accountBalance;
}
