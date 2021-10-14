package com.rox.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/entries")
@RestController
public class JournalEntriesController {
    @Autowired
    private JournalEntriesService journalEntriesService;

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
