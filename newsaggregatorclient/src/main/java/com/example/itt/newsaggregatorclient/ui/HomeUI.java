package com.example.itt.newsaggregatorclient.ui;

import com.example.itt.newsaggregatorclient.controller.AuthController;
import com.example.itt.newsaggregatorclient.interfaces.ConsoleUI;

import java.util.Scanner;

public class HomeUI implements ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthController authController = new AuthController();

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n==============================");
            System.out.println("Welcome to News Aggregator Application. Please choose the options below.");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    authController.handleLogin();
                    break;
                case "2":
                    authController.handleSignup();
                    break;
                case "3":
                    System.out.println("Exiting application");
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
