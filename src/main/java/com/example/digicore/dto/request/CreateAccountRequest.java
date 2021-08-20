package com.example.digicore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAccountRequest {

    @NotBlank(message = "AccountName is mandatory")
    private String accountName;
    @NotBlank(message = "Password  is mandatory")
    private String accountPassword;
    @NotBlank(message = "AccountName is mandatory")
    private Double initialDeposit;
}
