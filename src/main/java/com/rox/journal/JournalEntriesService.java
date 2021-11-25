package com.rox.journal;

import org.springframework.stereotype.Repository;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JournalEntriesService {
    private final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    //TODO This becomes a database at some point
    private List<JournalEntry> testEntries = new ArrayList<>();

    public boolean append(JournalEntry entry){
        entry.setCreation(new java.util.Date());
        return testEntries.add(entry);
    }

    public List<JournalEntry> list(final EntriesQuery query){
        //TODO Paging information, PagedList<JournalEntry> perhaps
        if (testEntries.size() <= query.getStart())
            return Collections.emptyList();
        int limit = query.getSize().orElse(testEntries.size());
        if (limit > testEntries.size())
            limit = testEntries.size();

        return testEntries.subList(
                    query.getStart(),
                    limit
                ).stream().filter(e -> {
            return e.getBody().contains(query.getBodyContains());
        }).collect(Collectors.toList());
    }

    public List<JournalEntry> list(){
        return testEntries;
    }

    public Map<String, JournalEntry> orderedEntryMap() {
        final Map<String, JournalEntry> collect = testEntries.stream().collect(Collectors.toMap(e -> formatter.format(e.getCreation()), e -> e));
        return collect;
    }
}
