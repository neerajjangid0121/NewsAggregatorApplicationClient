package com.example.itt.newsaggregatorclient.controller;

import com.example.itt.newsaggregatorclient.dto.ExternalAPIServerDTO;
import com.example.itt.newsaggregatorclient.service.AdminService;

import java.util.List;
import java.util.Scanner;

public class AdminUIController {

    private final AdminService adminService;
    private final Scanner scanner;

    public AdminUIController(AdminService adminService, Scanner scanner) {
        this.adminService = adminService;
        this.scanner = scanner;
    }

    public void viewExternalServers() {
        List<ExternalAPIServerDTO> servers = adminService.getAllServers();
        if (servers.isEmpty()) {
            System.out.println("No external servers found.");
        } else {
            System.out.println("\nList of external servers: ");
            for (ExternalAPIServerDTO server : servers) {
                System.out.printf("ID: %s | Name: %s | Active: %s%n | Last Accessed: %s%n", server.getId(), server.getServerName(), server.isActive(), server.getLastFetched());
            }
        }
    }

    public void viewServerDetails() {
        List<ExternalAPIServerDTO> servers = adminService.getAllServers();

        if (servers.isEmpty()) {
            System.out.println("No external servers found.");
        } else {
            System.out.println("\nList of external server details:");
            for (ExternalAPIServerDTO server : servers) {
                System.out.printf("%s. %s - %s%n", server.getId(), server.getServerName(), server.getApiKey());
            }
        }
    }

    public void updateServerDetails() {
        System.out.print("Enter server ID to update: ");
        String serverId = scanner.nextLine();

        System.out.print("Enter the updated API Key: ");
        String apiKey = scanner.nextLine();

        boolean success = adminService.updateServer(serverId, apiKey);
        System.out.println(success ? "Server updated successfully." : "Update failed.");
    }

    public void addNewsCategory() {
        System.out.print("Enter new category name: ");
        String category = scanner.nextLine();
        boolean added = adminService.addCategory(category);
        System.out.println(added ? "Category added successfully." : "Failed to add category.");
    }
}