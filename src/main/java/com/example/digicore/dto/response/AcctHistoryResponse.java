package com.example.digicore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcctHistoryResponse {
    private Date transactionDate;
    private String transactionType;
    private String narration;
    private Double amount;
    private Double accountBalance;
}
