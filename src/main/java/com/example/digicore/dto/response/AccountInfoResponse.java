package com.example.digicore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoResponse {
    private int responseCode;
    private boolean success;
    private String message;
    private AccountResponse accountResponse;
}
