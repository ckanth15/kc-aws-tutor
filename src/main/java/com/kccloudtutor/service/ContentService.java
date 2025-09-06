package com.kccloudtutor.service;

import com.kccloudtutor.model.Tutorial;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContentService {
    
    private List<Tutorial> tutorials = new ArrayList<>();
    private Map<String, List<Tutorial>> tutorialsByCategory = new HashMap<>();
    
    @PostConstruct
    public void loadContent() {
        try {
            loadTutorialsFromMarkdown();
            organizeTutorialsByCategory();
        } catch (Exception e) {
            System.err.println("Error loading content: " + e.getMessage());
            loadFallbackContent();
        }
    }
    
    private void loadTutorialsFromMarkdown() throws IOException {
        ClassPathResource resource = new ClassPathResource("content");
        Path contentDir = Paths.get(resource.getURI());
        
        if (!Files.exists(contentDir)) {
            System.out.println("Content directory not found, using fallback content");
            return;
        }
        
        Files.walk(contentDir)
            .filter(path -> path.toString().endsWith(".md"))
            .forEach(this::processMarkdownFile);
    }
    
    private void processMarkdownFile(Path filePath) {
        try {
            String content = Files.readString(filePath);
            Tutorial tutorial = parseMarkdownFile(content, filePath.getFileName().toString());
            if (tutorial != null) {
                tutorials.add(tutorial);
            }
        } catch (IOException e) {
            System.err.println("Error processing file " + filePath + ": " + e.getMessage());
        }
    }
    
    private Tutorial parseMarkdownFile(String content, String filename) {
        try {
            String[] parts = content.split("---", 3);
            if (parts.length < 3) {
                return createFallbackTutorial(filename, content);
            }
            
            String frontMatter = parts[1].trim();
            String markdownContent = parts[2].trim();
            
            Yaml yaml = new Yaml();
            Map<String, Object> metadata = yaml.load(frontMatter);
            
            Tutorial tutorial = new Tutorial();
            tutorial.setSlug(filename.replace(".md", ""));
            tutorial.setTitle((String) metadata.getOrDefault("title", "Untitled"));
            tutorial.setDescription((String) metadata.getOrDefault("description", ""));
            tutorial.setCategory((String) metadata.getOrDefault("category", "general"));
            tutorial.setDifficulty((String) metadata.getOrDefault("difficulty", "beginner"));
            tutorial.setDuration((String) metadata.getOrDefault("duration", ""));
            tutorial.setPublishedAt((String) metadata.getOrDefault("publishedAt", ""));
            tutorial.setUpdatedAt((String) metadata.getOrDefault("updatedAt", ""));
            tutorial.setAuthor((String) metadata.getOrDefault("author", "KC Cloud Tutor"));
            tutorial.setFeatured((Boolean) metadata.getOrDefault("featured", false));
            
            @SuppressWarnings("unchecked")
            List<String> tags = (List<String>) metadata.getOrDefault("tags", new ArrayList<>());
            tutorial.setTags(tags);
            
            // Convert markdown to HTML
            tutorial.setContent(convertMarkdownToHtml(markdownContent));
            
            return tutorial;
        } catch (Exception e) {
            System.err.println("Error parsing markdown file " + filename + ": " + e.getMessage());
            return createFallbackTutorial(filename, content);
        }
    }
    
    private Tutorial createFallbackTutorial(String filename, String content) {
        Tutorial tutorial = new Tutorial();
        tutorial.setSlug(filename.replace(".md", ""));
        tutorial.setTitle(filename.replace(".md", "").replace("-", " ").replace("_", " "));
        tutorial.setDescription("Tutorial content");
        tutorial.setCategory("general");
        tutorial.setDifficulty("beginner");
        tutorial.setDuration("30 min");
        tutorial.setPublishedAt("2024-01-01");
        tutorial.setUpdatedAt("2024-01-01");
        tutorial.setAuthor("KC Cloud Tutor");
        tutorial.setFeatured(false);
        tutorial.setTags(Arrays.asList("tutorial"));
        tutorial.setContent(convertMarkdownToHtml(content));
        return tutorial;
    }
    
    private String convertMarkdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        
        return renderer.render(parser.parse(markdown));
    }
    
    private void organizeTutorialsByCategory() {
        tutorialsByCategory = tutorials.stream()
            .collect(Collectors.groupingBy(Tutorial::getCategory));
    }
    
    private void loadFallbackContent() {
        // AWS Tutorials
        tutorials.add(createTutorial("aws-fundamentals", "AWS Fundamentals", 
            "Learn the core concepts and services of Amazon Web Services", "aws", 
            "beginner", "45 min", "2024-01-15", "2024-01-15", 
            Arrays.asList("aws", "fundamentals", "cloud"), "KC Cloud Tutor", true,
            "<h1>AWS Fundamentals</h1><p>Welcome to your AWS learning journey!</p>"));
            
        tutorials.add(createTutorial("ec2-basics", "EC2 Basics", 
            "Learn how to launch and manage virtual servers on AWS", "aws", 
            "beginner", "30 min", "2024-01-16", "2024-01-16", 
            Arrays.asList("aws", "ec2", "compute"), "KC Cloud Tutor", false,
            "<h1>EC2 Basics</h1><p>Master Amazon Elastic Compute Cloud.</p>"));
            
        tutorials.add(createTutorial("s3-fundamentals", "S3 Fundamentals", 
            "Master Amazon S3 object storage service", "aws", 
            "beginner", "25 min", "2024-01-17", "2024-01-17", 
            Arrays.asList("aws", "s3", "storage"), "KC Cloud Tutor", false,
            "<h1>S3 Fundamentals</h1><p>Learn object storage with Amazon S3.</p>"));
        
        // Azure Tutorials
        tutorials.add(createTutorial("azure-fundamentals", "Azure Fundamentals", 
            "Introduction to Microsoft Azure cloud platform", "azure", 
            "beginner", "40 min", "2024-01-18", "2024-01-18", 
            Arrays.asList("azure", "fundamentals", "cloud"), "KC Cloud Tutor", false,
            "<h1>Azure Fundamentals</h1><p>Get started with Microsoft Azure.</p>"));
        
        // GCP Tutorials
        tutorials.add(createTutorial("gcp-fundamentals", "GCP Fundamentals", 
            "Introduction to Google Cloud Platform", "gcp", 
            "beginner", "35 min", "2024-01-19", "2024-01-19", 
            Arrays.asList("gcp", "fundamentals", "cloud"), "KC Cloud Tutor", false,
            "<h1>GCP Fundamentals</h1><p>Explore Google Cloud Platform.</p>"));
        
        organizeTutorialsByCategory();
    }
    
    private Tutorial createTutorial(String slug, String title, String description, 
                                  String category, String difficulty, String duration, 
                                  String publishedAt, String updatedAt, List<String> tags, 
                                  String author, boolean featured, String content) {
        Tutorial tutorial = new Tutorial();
        tutorial.setSlug(slug);
        tutorial.setTitle(title);
        tutorial.setDescription(description);
        tutorial.setCategory(category);
        tutorial.setDifficulty(difficulty);
        tutorial.setDuration(duration);
        tutorial.setPublishedAt(publishedAt);
        tutorial.setUpdatedAt(updatedAt);
        tutorial.setTags(tags);
        tutorial.setAuthor(author);
        tutorial.setFeatured(featured);
        tutorial.setContent(content);
        return tutorial;
    }
    
    public List<Tutorial> getAllTutorials() {
        return tutorials;
    }
    
    public List<Tutorial> getTutorialsByCategory(String category) {
        return tutorialsByCategory.getOrDefault(category, new ArrayList<>());
    }
    
    public Tutorial getTutorialBySlug(String slug) {
        return tutorials.stream()
            .filter(t -> t.getSlug().equals(slug))
            .findFirst()
            .orElse(null);
    }
    
    public List<String> getAllCategories() {
        return tutorialsByCategory.keySet().stream()
            .sorted()
            .collect(Collectors.toList());
    }
    
    public List<Tutorial> getFeaturedTutorials() {
        return tutorials.stream()
            .filter(Tutorial::isFeatured)
            .collect(Collectors.toList());
    }
}
