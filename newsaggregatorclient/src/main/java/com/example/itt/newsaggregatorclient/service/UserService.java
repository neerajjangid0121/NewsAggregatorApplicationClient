package com.example.itt.newsaggregatorclient.service;

import com.example.itt.newsaggregatorclient.dto.ArticleDTO;
import com.example.itt.newsaggregatorclient.dto.NotificationDTO;
import com.example.itt.newsaggregatorclient.dto.NotificationSettingsDTO;
import com.example.itt.newsaggregatorclient.dto.SavedArticleRequestDTO;
import com.example.itt.newsaggregatorclient.dto.SavedArticleResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

public class UserService {
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/api/user"; // update if needed

    public UserService() {
        this.restTemplate = new RestTemplate();
    }


    public void getHeadlines(LocalDate startDate, LocalDate endDate, String category) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + "/headlines");
        urlBuilder.append("?startDate=").append(startDate);
        urlBuilder.append("&endDate=").append(endDate);
        if (category != null && !category.isBlank()) {
            urlBuilder.append("&category=").append(category);
        }

        try {
            ResponseEntity<ArticleDTO[]> response = restTemplate.getForEntity(
                    urlBuilder.toString(), ArticleDTO[].class);

            ArticleDTO[] articles = response.getBody();

            if (articles == null || articles.length == 0) {
                System.out.println("\n⚠ No articles found for the selected filters.");
            } else {
                System.out.println("\n📢 H E A D L I N E S");
                for (ArticleDTO article : articles) {
                    System.out.println("\n------------------------------");
                    System.out.println("Article Id: " + article.getId());
                    System.out.println(article.getTitle());
                    System.out.println(article.getDescription());
                    System.out.println("source: " + (article.getUrl() != null ?
                            article.getUrl().replaceAll("https?://(www\\.)?([^/]+).*", "$2") : "Unknown"));
                    System.out.println("URL: " + article.getUrl());
                    if (article.getCategories() != null && !article.getCategories().isEmpty()) {
                        System.out.println("Categories: " + String.join(", ", article.getCategories()));
                    }
                }
                System.out.println("\n------------------------------");
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch headlines: " + e.getMessage());
        }
    }

    public void saveArticle(Long articleId, Long userId) {
        String url = BASE_URL + "/saved";

        SavedArticleRequestDTO dto = new SavedArticleRequestDTO();
        dto.setUserId(userId);
        dto.setArticleId(articleId);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<SavedArticleRequestDTO> request = new HttpEntity<>(dto, headers);
            restTemplate.postForEntity(url, request, Void.class);
            System.out.println("✅ Article saved successfully!");
        } catch (Exception e) {
            System.out.println("❌ Failed to save article: " + e.getMessage());
        }
    }

    public void getSavedArticles(Long userId) {
        String url = BASE_URL + "/saved/" + userId;

        try {
            ResponseEntity<SavedArticleResponseDTO[]> response = restTemplate.getForEntity(
                    url, SavedArticleResponseDTO[].class);

            SavedArticleResponseDTO[] savedArticles = response.getBody();

            if (savedArticles == null || savedArticles.length == 0) {
                System.out.println("\n📚 No saved articles found.");
            } else {
                System.out.println("\n📚 S A V E D");
                for (SavedArticleResponseDTO savedArticle : savedArticles) {
                    System.out.println("\n------------------------------");
                    System.out.println("Article Id: " + savedArticle.getSavedArticleId());
                    System.out.println(savedArticle.getTitle());
                    System.out.println(savedArticle.getDescription());
                    System.out.println("source: " + (savedArticle.getUrl() != null ?
                            savedArticle.getUrl().replaceAll("https?://(www\\.)?([^/]+).*", "$2") : "Unknown"));
                    System.out.println("URL: " + savedArticle.getUrl());
                    if (savedArticle.getPublishedAt() != null) {
                        System.out.println("Published: " + savedArticle.getPublishedAt());
                    }
                    System.out.println("Saved At: " + savedArticle.getSavedAt());
                }
                System.out.println("\n------------------------------");
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch saved articles: " + e.getMessage());
        }
    }

    public void deleteSavedArticle(Long savedArticleId, Long userId) {
        String url = BASE_URL + "/saved/" + savedArticleId + "/user/" + userId;

        try {
            restTemplate.delete(url);
            System.out.println("✅ Saved article deleted successfully!");
        } catch (Exception e) {
            System.out.println("❌ Failed to delete saved article: " + e.getMessage());
        }
    }

    public void fetchLatestHeadlines() {
        System.out.println("🔹 Fetching latest headlines... (to be implemented)");
    }

    public void searchArticles(String query, LocalDate startDate, LocalDate endDate, String sortBy) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + "/search");
        urlBuilder.append("?query=").append(query);
        if (startDate != null) {
            urlBuilder.append("&startDate=").append(startDate);
        }
        if (endDate != null) {
            urlBuilder.append("&endDate=").append(endDate);
        }
        if (sortBy != null && !sortBy.isBlank()) {
            urlBuilder.append("&sortBy=").append(sortBy);
        }

        try {
            ResponseEntity<ArticleDTO[]> response = restTemplate.getForEntity(
                    urlBuilder.toString(), ArticleDTO[].class);

            ArticleDTO[] articles = response.getBody();

            if (articles == null || articles.length == 0) {
                System.out.println("\n⚠ No articles found for the search query: " + query);
            } else {
                System.out.println("\n🔍 S E A R C H");
                System.out.println("Results for \"" + query + "\"");
                for (ArticleDTO article : articles) {
                    System.out.println("\n------------------------------");
                    System.out.println("Article Id: " + article.getId());
                    System.out.println(article.getTitle());
                    System.out.println(article.getDescription());
                    System.out.println("source: " + (article.getUrl() != null ?
                            article.getUrl().replaceAll("https?://(www\\.)?([^/]+).*", "$2") : "Unknown"));
                    System.out.println("URL: " + article.getUrl());
                    if (article.getCategories() != null && !article.getCategories().isEmpty()) {
                        System.out.println("Categories: " + String.join(", ", article.getCategories()));
                    }
                }
                System.out.println("\n------------------------------");
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to search articles: " + e.getMessage());
        }
    }

    public void getNotifications(Long userId) {
        String url = BASE_URL + "/notifications/" + userId;

        try {
            ResponseEntity<NotificationDTO[]> response = restTemplate.getForEntity(url, NotificationDTO[].class);
            NotificationDTO[] notifications = response.getBody();

            if (notifications == null || notifications.length == 0) {
                System.out.println("\n🔔 No notifications found.");
            } else {
                System.out.println("\n🔔 N O T I F I C A T I O N S");
                for (NotificationDTO notification : notifications) {
                    System.out.println("\n------------------------------");
                    System.out.println("📰 " + notification.getArticleTitle());
                    System.out.println("📝 " + (notification.getArticleDescription() != null ?
                            notification.getArticleDescription().substring(0, Math.min(100, notification.getArticleDescription().length())) + "..." : "No description"));
                    System.out.println("🔗 " + notification.getArticleUrl());
                    System.out.println("🏷️ Trigger: " + notification.getNotificationType() + " - " + notification.getTriggerValue());
                    System.out.println("📅 Created: " + notification.getCreatedAt());
                    System.out.println("📖 Status: " + (notification.getIsRead() ? "✅ Read" : "🆕 Unread"));
                    System.out.println("🆔 Notification ID: " + notification.getId());
                }
                System.out.println("\n------------------------------");

                // Mark all notifications as read
                markAllNotificationsAsRead(notifications, userId);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to fetch notifications: " + e.getMessage());
        }
    }



    private void markAllNotificationsAsRead(NotificationDTO[] notifications, Long userId) {
        for (NotificationDTO notification : notifications) {
            if (!notification.getIsRead()) {
                markNotificationAsRead(notification.getId(), userId);
            }
        }
    }

    public void markNotificationAsRead(Long notificationId, Long userId) {
        String url = BASE_URL + "/notifications/" + notificationId + "/read?userId=" + userId;

        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.out.println("❌ Failed to mark notification as read: " + e.getMessage());
        }
    }

    public void configureCategoryNotification(Long userId, String category, boolean enabled) {
        String url = BASE_URL + "/notifications/settings";

        try {
            // First get current settings
            NotificationSettingsDTO currentSettings = getNotificationSettings(userId);

            // Update the specific category
            currentSettings.getCategorySettings().put(category, enabled);

            // Send updated settings to backend
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<NotificationSettingsDTO> request = new HttpEntity<>(currentSettings, headers);
            restTemplate.postForEntity(url, request, Void.class);

            System.out.println("✅ Notification settings updated for " + category + " (" + (enabled ? "enabled" : "disabled") + ")!");
        } catch (Exception e) {
            System.out.println("❌ Failed to update notification settings: " + e.getMessage());
        }
    }

    public NotificationSettingsDTO getNotificationSettings(Long userId) {
        String url = BASE_URL + "/notifications/settings/" + userId;

        try {
            ResponseEntity<NotificationSettingsDTO> response = restTemplate.getForEntity(url, NotificationSettingsDTO.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println("❌ Failed to get notification settings: " + e.getMessage());
            // Return default settings if failed
            return new NotificationSettingsDTO(userId, new java.util.HashMap<>(), new java.util.ArrayList<>());
        }
    }

    public void setNotificationKeywords(Long userId, String[] keywords) {
        String url = BASE_URL + "/notifications/keywords";

        try {
            // Create request body
            java.util.Map<String, Object> requestBody = new java.util.HashMap<>();
            requestBody.put("user_id", userId);
            requestBody.put("keywords", java.util.Arrays.asList(keywords));

            // Send request to backend
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<java.util.Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            restTemplate.postForEntity(url, request, Void.class);

            System.out.println("✅ Keywords updated successfully: " + String.join(", ", keywords));
        } catch (Exception e) {
            System.out.println("❌ Failed to update keywords: " + e.getMessage());
        }
    }

    public void showCurrentNotificationSettings(Long userId) {
        try {
            NotificationSettingsDTO settings = getNotificationSettings(userId);

            System.out.println("\n📋 CURRENT NOTIFICATION SETTINGS");
            System.out.println("--------------------------------");

            // Show category settings
            System.out.println("📂 Categories:");
            if (settings.getCategorySettings() != null) {
                for (java.util.Map.Entry<String, Boolean> entry : settings.getCategorySettings().entrySet()) {
                    String status = entry.getValue() ? "✅ Enabled" : "❌ Disabled";
                    System.out.println("   " + entry.getKey() + ": " + status);
                }
            }

            // Show keywords
            System.out.println("\n🔤 Keywords:");
            if (settings.getKeywords() != null && !settings.getKeywords().isEmpty()) {
                for (String keyword : settings.getKeywords()) {
                    System.out.println("   • " + keyword);
                }
            } else {
                System.out.println("   No keywords set");
            }
            System.out.println("--------------------------------");
        } catch (Exception e) {
            System.out.println("❌ Failed to load notification settings: " + e.getMessage());
        }
    }
}
