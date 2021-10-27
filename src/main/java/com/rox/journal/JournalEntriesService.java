package com.rox.journal;

import org.springframework.stereotype.Repository;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JournalEntriesService {
    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //TODO This becomes a database at some point
    private List<JournalEntry> testEntries = new ArrayList<>();

    public boolean add(JournalEntry entry){
        entry.setCreation(new java.util.Date());
        return testEntries.add(entry);
    }

    public List<JournalEntry> list(){
        return testEntries;
    }

    public Map<String, JournalEntry> orderedList() {
        final Map<String, JournalEntry> collect = testEntries.stream().collect(Collectors.toMap(e -> formatter.format(e.getCreation()), e -> e));
        return collect;
    }
}
