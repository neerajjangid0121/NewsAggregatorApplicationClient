package com.example.itt.newsaggregatorclient.service;

import com.example.itt.newsaggregatorclient.dto.ArticleDTO;
import com.example.itt.newsaggregatorclient.dto.ExternalAPIServerDTO;
import com.example.itt.newsaggregatorclient.dto.CategoryDTO;
import com.example.itt.newsaggregatorclient.dto.KeywordDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class AdminService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/api/admin"; // Update if needed
    private final String USER_URL = "http://localhost:8080/api/user"; // for hide/unhide

    public AdminService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ExternalAPIServerDTO> getAllServers() {
        String url = BASE_URL + "/external-servers";
        ResponseEntity<ExternalAPIServerDTO[]> response = restTemplate.getForEntity(url, ExternalAPIServerDTO[].class);
        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
    }

    public ExternalAPIServerDTO getServerDetails(String id) {
        String url = BASE_URL + "/external-servers/" + id;
        try {
            return restTemplate.getForObject(url, ExternalAPIServerDTO.class);
        } catch (Exception e) {
            System.out.println("Error fetching server: " + e.getMessage());
            return null;
        }
    }

    public boolean updateServer(String id,String apiKey) {
        String requestUrl = BASE_URL + "/external-servers/" + id;

        ExternalAPIServerDTO dto = new ExternalAPIServerDTO();
        dto.setId(id);
        dto.setApiKey(apiKey);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ExternalAPIServerDTO> entity = new HttpEntity<>(dto, headers);

            restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, Void.class);
            return true;
        } catch (Exception e) {
            System.out.println("Error updating server: " + e.getMessage());
            return false;
        }
    }

    public boolean addCategory(String categoryName) {
        String url = BASE_URL + "/categories";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(String.format("\"%s\"", categoryName), headers);
            restTemplate.postForEntity(url, request, Void.class);
            return true;
        } catch (Exception e) {
            System.out.println("Error adding category: " + e.getMessage());
            return false;
        }
    }

    // Fetch reported articles
    public List<ArticleDTO> getReportedArticles() {
        ResponseEntity<ArticleDTO[]> response = restTemplate.getForEntity(
                USER_URL + "/articles/reported", ArticleDTO[].class);
        return Arrays.asList(response.getBody());
    }

    // Hide or unhide an article
    public void toggleArticleVisibility(Long articleId, Long adminUserId, boolean hide, String reason) {
        String url = USER_URL + "/articles/" + articleId + "/visibility"
                + "?adminUserId=" + adminUserId
                + "&hide=" + hide
                + (reason != null && !reason.isEmpty() ? "&reason=" + reason : "");
        restTemplate.put(url, null);
    }

    // Category restriction management
    public List<CategoryDTO> getAllCategories() {
        String url = BASE_URL + "/categories";
        ResponseEntity<CategoryDTO[]> response = restTemplate.getForEntity(url, CategoryDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public void restrictCategory(Long categoryId, Long adminUserId, String reason) {
        String url = BASE_URL + "/categories/" + categoryId + "/restrict?adminUserId=" + adminUserId + "&reason=" + reason;
        restTemplate.postForEntity(url, null, Void.class);
    }

    public void unrestrictCategory(Long categoryId) {
        String url = BASE_URL + "/categories/" + categoryId + "/restrict";
        restTemplate.delete(url);
    }

    // Keyword restriction management
    public List<KeywordDTO> getAllKeywords() {
        String url = "http://localhost:8080/api/keywords";
        ResponseEntity<KeywordDTO[]> response = restTemplate.getForEntity(url, KeywordDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public void restrictKeyword(String keyword, Long adminUserId, String reason) {
        String url = "http://localhost:8080/api/keywords/restrict?keyword=" + keyword + "&adminUserId=" + adminUserId + "&reason=" + reason;
        restTemplate.postForEntity(url, null, Void.class);
    }

    public void unrestrictKeyword(String keyword) {
        String url = "http://localhost:8080/api/keywords/restrict?keyword=" + keyword;
        restTemplate.delete(url);
    }
}