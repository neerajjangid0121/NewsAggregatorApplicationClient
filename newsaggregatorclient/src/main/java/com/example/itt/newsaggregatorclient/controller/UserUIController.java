package com.example.itt.newsaggregatorclient.controller;
import com.example.itt.newsaggregatorclient.service.UserService;

import java.time.LocalDate;

public class UserUIController {

    private final UserService userService;

    public UserUIController(UserService userService) {
        this.userService = userService;
    }

    public void viewHeadlines() {
        userService.fetchLatestHeadlines();
    }

    public void getHeadlines(LocalDate startDate, LocalDate endDate, String category) {
        userService.getHeadlines(startDate, endDate, category);
    }

    public void saveArticle(Long articleId, Long userId) {
        userService.saveArticle(articleId, userId);
    }

    public void viewSavedArticles(Long userId) {
        userService.getSavedArticles(userId);
    }

    public void deleteSavedArticle(Long savedArticleId, Long userId) {
        userService.deleteSavedArticle(savedArticleId, userId);
    }

    public void searchArticles() {
        userService.searchArticles();
    }

    public void viewNotifications() {
        userService.getNotifications();
    }
}
