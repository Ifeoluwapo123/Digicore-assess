package com.example.digicore.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private int responseCode;
    private boolean success;
    private String message;
}
