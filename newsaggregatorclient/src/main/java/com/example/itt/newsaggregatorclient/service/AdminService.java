package com.example.itt.newsaggregatorclient.service;

import com.example.itt.newsaggregatorclient.dto.ExternalAPIServerDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class AdminService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/api/admin"; // Update if needed

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
}