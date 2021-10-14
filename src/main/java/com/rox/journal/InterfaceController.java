package com.rox.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterfaceController {
    @Autowired
    private JournalEntriesService journalEntriesService;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("name", "Ross");
        model.addAttribute("entries", journalEntriesService.list());
        model.addAllAttributes(journalEntriesService.orderedList());
        return "journal";
    }
}
