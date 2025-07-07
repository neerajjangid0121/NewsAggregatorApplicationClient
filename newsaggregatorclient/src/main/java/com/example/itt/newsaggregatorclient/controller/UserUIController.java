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

    public void searchArticles(String query, LocalDate startDate, LocalDate endDate, String sortBy) {
        userService.searchArticles(query, startDate, endDate, sortBy);
    }

    public void viewNotifications(Long userId) {
        userService.getNotifications(userId);
    }

    public void configureCategoryNotification(Long userId, String category, boolean enabled) {
        userService.configureCategoryNotification(userId, category, enabled);
    }

    public void setNotificationKeywords(Long userId, String[] keywords) {
        userService.setNotificationKeywords(userId, keywords);
    }

    public void showCurrentNotificationSettings(Long userId) {
        userService.showCurrentNotificationSettings(userId);
    }

    public void reportArticle(Long articleId, Long userId, String reason) {
        userService.reportArticle(articleId, userId, reason);
    }

    public void likeArticle(Long articleId, Long userId) {
        userService.likeArticle(articleId, userId);
    }

    public void dislikeArticle(Long articleId, Long userId) {
        userService.dislikeArticle(articleId, userId);
    }
}
