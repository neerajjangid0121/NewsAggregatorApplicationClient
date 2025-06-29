package com.example.itt.newsaggregatorclient.controller;

import com.example.itt.newsaggregatorclient.dto.UserLoginResponseDTO;
import com.example.itt.newsaggregatorclient.dto.UserSignUpResponseDTO;
import com.example.itt.newsaggregatorclient.service.AdminService;
import com.example.itt.newsaggregatorclient.service.AuthService;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class AuthController {
    private final AuthService authService;
    private final Scanner scanner;

    public AuthController() {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    public void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserLoginResponseDTO response = authService.login(username, password);

        if (response != null) {
            System.out.println("Welcome to the News Application, " + response.getUsername()+"! Date: ");

            switch (response.getRole().toUpperCase()) {
                case "ADMIN":
                    RestTemplate restTemplate = new RestTemplate();
                    AdminService adminService = new AdminService(restTemplate);
                    AdminUIController adminUIController = new AdminUIController(adminService, scanner);
                    new com.example.itt.newsaggregatorclient.ui.AdminUI(adminUIController, scanner).showMenu();
                    break;
                case "USER":
                    new com.example.itt.newsaggregatorclient.ui.UserUI().showMenu();
                    break;
                default:
                    System.out.println("Unknown role: " + response.getRole());
            }

        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    public void handleSignup() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserSignUpResponseDTO response = authService.register(username, email, password);

        if (response != null) {
            System.out.println("✅ Registration successful! You can now log in.");
        } else {
            System.out.println("❌ Registration failed. ");
        }
    }

}
