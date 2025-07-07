package com.example.itt.newsaggregatorclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArticleDTO {
    private Long id;
    private String title;
    private String publishedAt;
    private String url;
    private String description;
    private String content;
    private String serverName;
    private List<String> categories;
    private Integer reportCount;
    private String status;
}
