package com.example.itt.newsaggregatorclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponseDTO {
    private String username;
    private String email;
    private String role;
}
