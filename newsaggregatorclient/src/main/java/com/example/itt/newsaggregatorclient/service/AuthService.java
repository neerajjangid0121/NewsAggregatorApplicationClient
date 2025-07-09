package com.example.itt.newsaggregatorclient.service;
import com.example.itt.newsaggregatorclient.dto.UserSignUpRequestDTO;
import com.example.itt.newsaggregatorclient.dto.UserSignUpResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.itt.newsaggregatorclient.dto.UserLoginRequestDTO;
import com.example.itt.newsaggregatorclient.dto.UserLoginResponseDTO;

public class AuthService {

    private static final String BASE_URL = "http://localhost:8080/api/auth";

    public UserLoginResponseDTO login(String username, String password) {
        UserLoginRequestDTO loginRequest = new UserLoginRequestDTO(username, password);
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<UserLoginRequestDTO> request = new HttpEntity<>(loginRequest);
            ResponseEntity<UserLoginResponseDTO> response = restTemplate.postForEntity(
                    BASE_URL + "/login",
                    request,
                    UserLoginResponseDTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    public UserSignUpResponseDTO register(String username, String email, String password) {
        UserSignUpRequestDTO registerRequest = new UserSignUpRequestDTO(username, email, password);
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<UserSignUpRequestDTO> request = new HttpEntity<>(registerRequest);
            ResponseEntity<UserSignUpResponseDTO> response = restTemplate.postForEntity(
                    BASE_URL + "/register",
                    request,
                    UserSignUpResponseDTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            return null;
        }
    }
}