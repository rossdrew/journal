package com.rox.journal;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entries")
@RestController
public class JournalEntriesController {
    private final JournalEntriesService journalEntriesService;

    public JournalEntriesController(JournalEntriesService journalEntriesService) throws InterruptedException {
        this.journalEntriesService = journalEntriesService;
        /* DEBUG */
        for (int i=0; i<25; i++) {
            journalEntriesService.add(new JournalEntry("This is TEST journal entry No." + i));
            Thread.sleep(10);
        }
        /* /DEBUG */
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() { //Used for SPA to stop CORS issues, what is the correct method
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    /**
     * @param containsString filter entries by those whose bodies contain this string
     * @param start only show entries from this index forward
     * @param limit only show this number of entries
     *
     * @return a list of entries with optional filtering
     */
    @GetMapping()
    public List<JournalEntry> fetchEntries(
            @RequestParam(value = "contains", required = false) Optional<String> containsString,
            @RequestParam(value = "start", required = false) Optional<Integer> start,
            @RequestParam(value = "limit", required = false) Optional<Integer> limit
    ){
        return journalEntriesService.list(
                EntriesQuery.all()
                            .whereBodyContains(containsString)
                            .startingAtIndex(start));
    }

    @PostMapping("/append")
    public String createEntry(@RequestBody JournalEntry entry){
        journalEntriesService.add(entry);
        return entry.getBody();
    }
}
