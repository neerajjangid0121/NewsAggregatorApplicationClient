package com.example.itt.newsaggregatorclient.ui;

import com.example.itt.newsaggregatorclient.controller.AdminUIController;
import com.example.itt.newsaggregatorclient.interfaces.ConsoleUI;

import java.util.Scanner;

public class AdminUI implements ConsoleUI {

    private final AdminUIController adminUIController;
    private final Scanner scanner;

    public AdminUI(AdminUIController adminUIController, Scanner scanner) {
        this.adminUIController = adminUIController;
        this.scanner = scanner;
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. View the list of external servers and status");
            System.out.println("2. View the external server’s details");
            System.out.println("3. Update/Edit the external server’s details");
            System.out.println("4. Add new News Category");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    adminUIController.viewExternalServers();
                    break;
                case "2":
                    adminUIController.viewServerDetails();
                    break;
                case "3":
                    adminUIController.updateServerDetails();
                    break;
                case "4":
                    adminUIController.addNewsCategory();
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}