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
                    showSearchMenu();
                    break;
                case "4":
                    showNotificationsMenu();
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
        System.out.println("\n1. Back");
        System.out.println("2. Logout");
        System.out.println("3. Save Article");
        System.out.println("4. Report Article");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                return; // Back to previous menu
            case "2":
                System.out.println("Logging out...");
                return;
            case "3":
                System.out.print("Enter Article Id to save: ");
                String idStr = scanner.nextLine();
                Long articleId = Long.parseLong(idStr);
                userUIController.saveArticle(articleId, user.getUserId());
                break;
            case "4":
                System.out.print("Enter Article Id to save: ");
                String id = scanner.nextLine();
                System.out.print("Enter Reason (optional): ");
                String reason = scanner.nextLine();
                Long articleIdForReport = Long.parseLong(id);
                userUIController.reportArticle(articleIdForReport, user.getUserId(), reason);
                System.out.println("Article Reported Successfully");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
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

    private void showSearchMenu() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        System.out.print("Enter start date (yyyy-MM-dd) or leave blank: ");
        String start = scanner.nextLine();
        System.out.print("Enter end date (yyyy-MM-dd) or leave blank: ");
        String end = scanner.nextLine();
        System.out.print("Sort by (likes/dislikes) or leave blank: ");
        String sortBy = scanner.nextLine();

        LocalDate startDate = null, endDate = null;
        try {
            if (!start.isBlank()) startDate = LocalDate.parse(start);
        } catch (Exception ignored) {}
        try {
            if (!end.isBlank()) endDate = LocalDate.parse(end);
        } catch (Exception ignored) {}

        userUIController.searchArticles(query, startDate, endDate, sortBy);
        showSaveArticlePrompt();
    }

    private void showNotificationsMenu() {
        while (true) {
            System.out.println("\n=== NOTIFICATIONS ===");
            System.out.println("1. View Notifications");
            System.out.println("2. Configure Notifications");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userUIController.viewNotifications(user.getUserId());
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
        while (true) {
            System.out.println("\n=== CONFIGURE NOTIFICATIONS ===");

            // Show current settings
            userUIController.showCurrentNotificationSettings(user.getUserId());

            System.out.println("\n1. Business");
            System.out.println("2. Entertainment");
            System.out.println("3. Sports");
            System.out.println("4. Technology");
            System.out.println("5. Keywords");
            System.out.println("6. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                case "2":
                case "3":
                case "4":
                    String category = switch (choice) {
                        case "1" -> "Business";
                        case "2" -> "Entertainment";
                        case "3" -> "Sports";
                        case "4" -> "Technology";
                        default -> "";
                    };
                    System.out.printf("Enable notifications for %s? (y/n): ", category);
                    String yn = scanner.nextLine();
                    boolean enabled = yn.equalsIgnoreCase("y");
                    userUIController.configureCategoryNotification(user.getUserId(), category, enabled);
                    break;
                case "5":
                    System.out.print("Enter keywords (comma separated): ");
                    String keywordsStr = scanner.nextLine();
                    String[] keywords = keywordsStr.split(",");
                    for (int i = 0; i < keywords.length; i++) keywords[i] = keywords[i].trim();
                    userUIController.setNotificationKeywords(user.getUserId(), keywords);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}