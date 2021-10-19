package com.rox.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/entries")
@RestController
public class JournalEntriesController {
    private final JournalEntriesService journalEntriesService;

    public JournalEntriesController(JournalEntriesService journalEntriesService) {
        this.journalEntriesService = journalEntriesService;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @GetMapping()
    public List<JournalEntry> fetchEntries(@RequestParam(value = "contains", required = false) Optional<String> containsString){
        return journalEntriesService.list().stream()
                .filter(e -> e.getBody()
                        .contains(containsString.orElse("")))
                .collect(Collectors.toList());

        //TODO Paging
    }

    @PostMapping("/append")
    public String createEntry(@RequestBody JournalEntry entry){
        journalEntriesService.add(entry);
        return entry.getBody();
    }
}
