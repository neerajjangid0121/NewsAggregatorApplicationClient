package com.example.itt.newsaggregatorclient.ui;

import com.example.itt.newsaggregatorclient.controller.AdminUIController;
import com.example.itt.newsaggregatorclient.dto.ArticleDTO;
import com.example.itt.newsaggregatorclient.dto.CategoryDTO;
import com.example.itt.newsaggregatorclient.dto.KeywordDTO;
import com.example.itt.newsaggregatorclient.dto.UserLoginResponseDTO;
import com.example.itt.newsaggregatorclient.interfaces.ConsoleUI;

import java.util.List;
import java.util.Scanner;

public class AdminUI implements ConsoleUI {

    private final AdminUIController adminUIController;
    UserLoginResponseDTO response;
    private final Scanner scanner;

    public AdminUI(AdminUIController adminUIController, UserLoginResponseDTO response, Scanner scanner) {
        this.adminUIController = adminUIController;
        this.response = response;
        this.scanner = scanner;
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. View the list of external servers and status");
            System.out.println("2. View the external server's details");
            System.out.println("3. Update/Edit the external server's details");
            System.out.println("4. Add new News Category");
            System.out.println("5. View Reported Articles");
            System.out.println("6. Manage Restrictions");
            System.out.println("7. Logout");
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
                    viewReportedArticles();
                    break;
                case "6":
                    manageRestrictions();
                    break;
                case "7":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewReportedArticles() {
        List<ArticleDTO> reported = adminUIController.getReportedArticles();
        if (reported.isEmpty()) {
            System.out.println("No reported articles.");
            return;
        }
        for (int i = 0; i < reported.size(); i++) {
            ArticleDTO article = reported.get(i);
            System.out.printf("%d %s (Reports: %d) Current Status : %s\n",i+1, article.getTitle(), article.getReportCount(),article.getStatus());
        }
        System.out.print("Select article to hide/unhide: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx > 0 && idx <= reported.size()) {
            ArticleDTO article = reported.get(idx - 1);
            System.out.printf("Current status: %s\n", article.getStatus());
            System.out.print("Hide this article? (y/n): ");
            String input = scanner.nextLine();
            boolean hide = input.equalsIgnoreCase("y");
            System.out.print("Reason (optional): ");
            String reason = scanner.nextLine();
            adminUIController.toggleArticleVisibility(article.getId(), response.getUserId(), hide, reason);
            System.out.println("Article status updated.");
        }
    }

    private void manageRestrictions() {
        while (true) {
            System.out.println("\n=== MANAGE RESTRICTIONS ===");
            System.out.println("1. Manage Categories");
            System.out.println("2. Manage Keywords");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    manageCategoryRestrictions();
                    break;
                case "2":
                    manageKeywordRestrictions();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageCategoryRestrictions() {
        while (true) {
            System.out.println("\n=== CATEGORY RESTRICTIONS ===");
            List<CategoryDTO> categories = adminUIController.getAllCategories();
            for (CategoryDTO cat : categories) {
                System.out.printf("ID: %d | Name: %s | Restricted: %s\n", cat.getCategoryId(), cat.getName(), cat.getIsRestricted() ? "Yes" : "No");
            }
            System.out.println("1. Restrict a category");
            System.out.println("2. Unrestrict a category");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter category ID to restrict: ");
                    Long restrictId = Long.parseLong(scanner.nextLine());
                    System.out.print("Enter reason for restriction: ");
                    String reason = scanner.nextLine();
                    adminUIController.restrictCategory(restrictId, response.getUserId(), reason);
                    System.out.println("Category restricted.");
                    break;
                case "2":
                    System.out.print("Enter category ID to unrestrict: ");
                    Long unrestrictId = Long.parseLong(scanner.nextLine());
                    adminUIController.unrestrictCategory(unrestrictId);
                    System.out.println("Category unrestricted.");
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageKeywordRestrictions() {
        while (true) {
            System.out.println("\n=== KEYWORD RESTRICTIONS ===");
            List<KeywordDTO> keywords = adminUIController.getAllKeywords();
            for (KeywordDTO k : keywords) {
                System.out.printf("Keyword: %s | Restricted: %s\n", k.getKeyword(), k.getIsRestricted() ? "Yes" : "No");
            }
            System.out.println("1. Restrict a keyword");
            System.out.println("2. Unrestrict a keyword");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter keyword to restrict: ");
                    String restrictKeyword = scanner.nextLine();
                    System.out.print("Enter reason for restriction: ");
                    String reason = scanner.nextLine();
                    adminUIController.restrictKeyword(restrictKeyword, response.getUserId(), reason);
                    System.out.println("Keyword restricted.");
                    break;
                case "2":
                    System.out.print("Enter keyword to unrestrict: ");
                    String unrestrictKeyword = scanner.nextLine();
                    adminUIController.unrestrictKeyword(unrestrictKeyword);
                    System.out.println("Keyword unrestricted.");
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}