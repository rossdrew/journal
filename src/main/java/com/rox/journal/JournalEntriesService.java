package com.rox.journal;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JournalEntriesService {
    //TODO This becomes a database at some point
    private List<JournalEntry> testEntries = new ArrayList<>();

    public boolean append(JournalEntry entry){
        entry.setCreation(new java.util.Date());
        return testEntries.add(entry);
    }

    public PageWrapper<JournalEntry> list(final EntriesQuery query){
        if (testEntries.size() <= query.getStart())
            return PageWrapper.around(Collections.emptyList());
        int limit = query.getSize().orElse(testEntries.size() - query.getStart());
        if (limit > testEntries.size())
            limit = testEntries.size();

        final List<JournalEntry> results = testEntries.subList(
                query.getStart(),
                query.getStart() + limit
        ).stream().filter(e -> {
            return e.getBody().contains(query.getBodyContains());
        }).collect(Collectors.toList());

        PageWrapper<JournalEntry> page =
                PageWrapper.around(results)
                          .startingAt(query.getStart())
                          .fromPoolOf(testEntries.size());

        if (query.getSize().isPresent()){
            page = page.limitedTo(query.getSize().get());
        }

        return page;
    }
}
