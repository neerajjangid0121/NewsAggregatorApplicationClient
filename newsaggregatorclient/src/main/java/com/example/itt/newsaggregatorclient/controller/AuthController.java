package com.example.itt.newsaggregatorclient.controller;

import com.example.itt.newsaggregatorclient.dto.UserLoginResponseDTO;
import com.example.itt.newsaggregatorclient.dto.UserSignUpResponseDTO;
import com.example.itt.newsaggregatorclient.service.AdminService;
import com.example.itt.newsaggregatorclient.service.AuthService;
import com.example.itt.newsaggregatorclient.service.UserService;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));
            System.out.printf("Welcome to the News Application, %s! Date: %s\nTime: %s\n", response.getUsername(), formattedDate, formattedTime);

            switch (response.getRole().toUpperCase()) {
                case "ADMIN":
                    RestTemplate restTemplate = new RestTemplate();
                    AdminService adminService = new AdminService(restTemplate);
                    AdminUIController adminUIController = new AdminUIController(adminService, scanner);
                    new com.example.itt.newsaggregatorclient.ui.AdminUI(adminUIController, scanner).showMenu();
                    break;
                case "USER":
                    System.out.println(response.getUserId());
                    new com.example.itt.newsaggregatorclient.ui.UserUI(new UserUIController(new UserService()),response,new Scanner(System.in)).showMenu();
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
