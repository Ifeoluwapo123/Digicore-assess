package com.example.digicore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WithdrawRequest {
    private String accountNumber;
    private String accountPassword;
    private Double withdrawnAmount;
    private String narration;
}
