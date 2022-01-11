package com.rox.journal;

import com.rox.journal.entry.EntriesQuery;
import com.rox.journal.entry.JournalEntriesService;
import com.rox.journal.entry.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class InterfaceController {
    @Autowired
    private JournalEntriesService journalEntriesService;

    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("name", "Ross");
        final Map<String, JournalEntry> entries = journalEntriesService.list(EntriesQuery.all())
                .getData()
                .stream()
                .collect(Collectors.toMap(e -> formatter.format(e.getCreation()), e -> e));
        model.addAllAttributes(entries);
        return "journal";
    }
}
