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

    public List<JournalEntry> list(final EntriesQuery query){
        //TODO Paging information, PagedList<JournalEntry> perhaps
        if (testEntries.size() <= query.getStart())
            return Collections.emptyList();
        int limit = query.getSize().orElse(testEntries.size() - query.getStart());
        if (limit > testEntries.size())
            limit = testEntries.size();

        return testEntries.subList(
                    query.getStart(),
                    query.getStart() + limit
                ).stream().filter(e -> {
            return e.getBody().contains(query.getBodyContains());
        }).collect(Collectors.toList());
    }

    public PageWrapper<JournalEntry> pagedList(final EntriesQuery query){
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

        return PageWrapper.around(results)
                          .startingAt(query.getStart())
                          .limitedTo(limit)
                          .fromPoolOf(testEntries.size());
    }

    public List<JournalEntry> list(){
        return testEntries;
    }
}
