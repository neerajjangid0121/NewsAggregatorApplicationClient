package com.example.itt.newsaggregatorclient.service;

import com.example.itt.newsaggregatorclient.dto.ArticleDTO;
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
                System.out.println("\n📢 Headlines:");
                for (ArticleDTO article : articles) {
                    System.out.println("\n------------------------------");
                    System.out.println("🆔 ID        : " + article.getId());
                    System.out.println("📰 Title     : " + article.getTitle());
                    System.out.println("📅 Published : " + article.getPublishedAt());
                    System.out.println("📌 Source    : " + article.getServerName());
                    System.out.println("🏷 Categories: " + String.join(", ", article.getCategories()));
                    System.out.println("🔗 URL       : " + article.getUrl());
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
                System.out.println("\n📚 Your Saved Articles:");
                for (SavedArticleResponseDTO savedArticle : savedArticles) {
                    System.out.println("\n------------------------------");
                    System.out.println("💾 Saved ID   : " + savedArticle.getSavedArticleId());
                    System.out.println("📰 Article ID : " + savedArticle.getArticleId());
                    System.out.println("📝 Title      : " + savedArticle.getTitle());
                    System.out.println("📄 Description: " + savedArticle.getDescription());
                    System.out.println("📅 Published  : " + savedArticle.getPublishedAt());
                    System.out.println("💾 Saved At   : " + savedArticle.getSavedAt());
                    System.out.println("🔗 URL        : " + savedArticle.getUrl());
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

    public void getNotifications() {
        System.out.println("🔹 Fetching your notifications... (to be implemented)");
    }
}
