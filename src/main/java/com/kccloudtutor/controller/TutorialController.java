package com.kccloudtutor.controller;

import com.kccloudtutor.model.Tutorial;
import com.kccloudtutor.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TutorialController {
    
    @Autowired
    private ContentService contentService;
    
    @GetMapping("/")
    public String home(Model model) {
        List<String> categories = contentService.getAllCategories();
        List<Tutorial> featuredTutorials = contentService.getFeaturedTutorials();
        
        model.addAttribute("categories", categories);
        model.addAttribute("featuredTutorials", featuredTutorials);
        return "index";
    }
    
    @GetMapping("/category/{category}")
    public String category(@PathVariable String category, Model model) {
        List<Tutorial> tutorials = contentService.getTutorialsByCategory(category);
        List<String> categories = contentService.getAllCategories();
        
        model.addAttribute("currentCategory", category);
        model.addAttribute("tutorials", tutorials);
        model.addAttribute("categories", categories);
        return "category";
    }
    
    @GetMapping("/tutorial/{slug}")
    public String tutorial(@PathVariable String slug, Model model) {
        Tutorial tutorial = contentService.getTutorialBySlug(slug);
        if (tutorial == null) {
            return "redirect:/";
        }
        
        List<Tutorial> relatedTutorials = contentService.getTutorialsByCategory(tutorial.getCategory())
            .stream()
            .filter(t -> !t.getSlug().equals(slug))
            .limit(3)
            .toList();
        
        model.addAttribute("tutorial", tutorial);
        model.addAttribute("relatedTutorials", relatedTutorials);
        return "tutorial";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Tutorial> allTutorials = contentService.getAllTutorials();
        List<Tutorial> searchResults = allTutorials.stream()
            .filter(tutorial -> 
                tutorial.getTitle().toLowerCase().contains(q.toLowerCase()) ||
                tutorial.getDescription().toLowerCase().contains(q.toLowerCase()) ||
                tutorial.getTags().stream().anyMatch(tag -> 
                    tag.toLowerCase().contains(q.toLowerCase())
                )
            )
            .toList();
        
        model.addAttribute("query", q);
        model.addAttribute("results", searchResults);
        return "search";
    }
}
