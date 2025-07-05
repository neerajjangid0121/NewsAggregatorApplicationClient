package com.example.itt.newsaggregatorclient.service;

import com.example.itt.newsaggregatorclient.dto.ArticleDTO;
import com.example.itt.newsaggregatorclient.dto.SavedArticleRequestDTO;
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

    public void fetchLatestHeadlines() {
        System.out.println("🔹 Fetching latest headlines... (to be implemented)");
    }

    public void getSavedArticles() {
        System.out.println("🔹 Displaying saved articles... (to be implemented)");
    }

    public void searchArticles() {
        System.out.println("🔹 Searching articles... (to be implemented)");
    }

    public void getNotifications() {
        System.out.println("🔹 Fetching your notifications... (to be implemented)");
    }
}
