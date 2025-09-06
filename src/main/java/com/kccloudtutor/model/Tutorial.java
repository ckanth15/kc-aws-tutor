package com.kccloudtutor.model;

import java.util.List;
import java.util.Map;

public class Tutorial {
    private String slug;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private String duration;
    private String publishedAt;
    private String updatedAt;
    private List<String> tags;
    private String author;
    private boolean featured;
    private String content;

    // Constructors
    public Tutorial() {}

    public Tutorial(String slug, String title, String description, String category, 
                   String difficulty, String duration, String publishedAt, String updatedAt, 
                   List<String> tags, String author, boolean featured, String content) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.duration = duration;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
        this.author = author;
        this.featured = featured;
        this.content = content;
    }

    // Getters and Setters
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
