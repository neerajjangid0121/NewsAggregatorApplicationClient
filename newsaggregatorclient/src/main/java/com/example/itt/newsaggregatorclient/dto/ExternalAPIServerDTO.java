package com.example.itt.newsaggregatorclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAPIServerDTO {
    private String id;
    private String serverName;
    private String apiKey;
    private String url;
    private boolean isActive;
    private LocalDateTime lastFetched;
}
