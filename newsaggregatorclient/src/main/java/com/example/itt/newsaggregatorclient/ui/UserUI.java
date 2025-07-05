package com.example.itt.newsaggregatorclient.ui;

import com.example.itt.newsaggregatorclient.controller.UserUIController;
import com.example.itt.newsaggregatorclient.dto.UserLoginResponseDTO;
import com.example.itt.newsaggregatorclient.interfaces.ConsoleUI;

import java.time.LocalDate;
import java.util.Scanner;

public class UserUI implements ConsoleUI {

    private final UserUIController userUIController;
    private final UserLoginResponseDTO user;
    private final Scanner scanner;


    public UserUI(UserUIController userUIController, UserLoginResponseDTO user, Scanner scanner) {
        this.userUIController = userUIController;
        this.user = user;
        this.scanner = scanner;
    }


    @Override
    public void showMenu() {

        System.out.println("Please choose the options below");

        while (true) {
            System.out.println("\n1. Headlines");
            System.out.println("2. Saved Articles");
            System.out.println("3. Search");
            System.out.println("4. Notifications");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showHeadlinesMenu();
                    break;
                case "2":
                    showSavedArticlesMenu();
                    break;
                case "3":
                    //showSearchMenu();
                    break;
                case "4":
                    //showNotificationsMenu();
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showHeadlinesMenu() {
        while (true) {
            System.out.println("\nHeadlines Menu:");
            System.out.println("1. Today");
            System.out.println("2. Date range");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showHeadlinesCategoryMenu(true);
                    break;
                case "2":
                    showHeadlinesCategoryMenu(false);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showHeadlinesCategoryMenu(boolean isToday) {
        LocalDate startDate;
        LocalDate endDate;

        if (isToday) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();
            userUIController.getHeadlines(startDate, endDate, null);
        } else {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (YYYY-MM-DD): ");
            endDate = LocalDate.parse(scanner.nextLine());

            System.out.println("\nPlease choose the category:");
            System.out.println("1. All");
            System.out.println("2. Business");
            System.out.println("3. Entertainment");
            System.out.println("4. Sports");
            System.out.println("5. Technology");
            System.out.print("Enter your choice: ");
            String categoryChoice = scanner.nextLine();

            String category = switch (categoryChoice) {
                case "2" -> "Business";
                case "3" -> "Entertainment";
                case "4" -> "Sports";
                case "5" -> "Technology";
                default -> "All";
            };
            userUIController.getHeadlines(startDate, endDate, category);
        }


        showSaveArticlePrompt();
    }


    private void showSaveArticlePrompt() {
        System.out.println("\nOptions: 1. Save Article 2. Back");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        if ("1".equals(choice)) {
            System.out.print("Enter Article Id to save: ");
            String idStr = scanner.nextLine();
            try {
                Long articleId = Long.parseLong(idStr);
                userUIController.saveArticle(articleId, user.getUserId()); // ✅ pass userId
            } catch (Exception e) {
                System.out.println("Invalid Article Id.");
            }
        }
    }

    private void showSavedArticlesMenu() {
        userUIController.viewSavedArticles(user.getUserId());
        System.out.println("\n1. Back");
        System.out.println("2. Logout");
        System.out.println("3. Delete Article");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                return; // Back to main menu
            case "2":
                System.out.println("Logging out...");
                return;
            case "3":
                System.out.print("Enter Saved Article ID to delete: ");
                String idStr = scanner.nextLine();
                try {
                    Long savedArticleId = Long.parseLong(idStr);
                    userUIController.deleteSavedArticle(savedArticleId, user.getUserId());
                } catch (Exception e) {
                    System.out.println("Invalid Saved Article ID.");
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
/*
    private void showSearchMenu() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        System.out.print("Enter start date (yyyy-MM-dd) or leave blank: ");
        String start = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd) or leave blank: ");
        String end = scanner.nextLine();
        System.out.print("Sort by (likes/dislikes) or leave blank: ");
        String sortBy = scanner.nextLine();
        java.time.LocalDate startDate = null, endDate = null;
        try { if (!start.isBlank()) startDate = java.time.LocalDate.parse(start); } catch (Exception ignored) {}
        try { if (!end.isBlank()) endDate = java.time.LocalDate.parse(end); } catch (Exception ignored) {}
        userUIController.searchArticles(query, startDate, endDate, sortBy);
        showSaveArticlePrompt();
    }

    private void showNotificationsMenu() {
        while (true) {
            System.out.println("\nNOTIFICATIONS");
            System.out.println("1. View Notifications");
            System.out.println("2. Configure Notifications");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userUIController.viewNotifications(user.getEmail());
                    break;
                case "2":
                    showConfigureNotificationsMenu();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showConfigureNotificationsMenu() {
        String[] categories = {"Business", "Entertainment", "Sports", "Technology", "Keywords"};
        while (true) {
            System.out.println("\nCONFIGURE NOTIFICATIONS");
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s\n", i + 1, categories[i]);
            }
            System.out.println((categories.length + 1) + ". Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            int idx;
            try { idx = Integer.parseInt(choice) - 1; } catch (Exception e) { idx = -1; }
            if (idx >= 0 && idx < categories.length) {
                if (categories[idx].equals("Keywords")) {
                    System.out.print("Enter keywords (comma separated): ");
                    String keywordsStr = scanner.nextLine();
                    String[] keywords = keywordsStr.split(",");
                    for (int i = 0; i < keywords.length; i++) keywords[i] = keywords[i].trim();
                    userUIController.setNotificationKeywords(user.getEmail(), keywords);
                } else {
                    System.out.printf("Enable notifications for %s? (y/n): ", categories[idx]);
                    String yn = scanner.nextLine();
                    boolean enabled = yn.equalsIgnoreCase("y");
                    userUIController.configureNotification(user.getEmail(), categories[idx].toLowerCase(), enabled);
                }
            } else if (idx == categories.length) {
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }*/
}