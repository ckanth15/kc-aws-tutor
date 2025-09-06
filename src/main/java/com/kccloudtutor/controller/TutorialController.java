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
        // For now, just return the basic template without dynamic data
        return "index";
    }
    
    @GetMapping("/category/{category}")
    public String category(@PathVariable String category, Model model) {
        model.addAttribute("selectedCategory", category);
        return "category";
    }
    
    @GetMapping("/category/{category}/{tutorialSlug}")
    public String tutorial(@PathVariable String category, @PathVariable String tutorialSlug, Model model) {
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedTutorial", tutorialSlug);
        return "tutorial";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
