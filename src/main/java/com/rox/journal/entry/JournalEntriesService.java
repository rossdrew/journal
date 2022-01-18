package com.rox.journal.entry;

import com.rox.journal.PageWrapper;
import com.rox.journal.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class JournalEntriesService {
    //TODO This becomes a database at some point
    private List<JournalEntry> testEntries = new ArrayList<>();

    private static final Pattern starting = Pattern.compile("_([^_]*)_");
    private static final Pattern completing = Pattern.compile("--([^--]*)--");

    public JournalEntry append(JournalEntry entry){
        entry = entry.at(new java.util.Date());

        final Matcher workStarting = starting.matcher(entry.getBody());
        while (workStarting.find()){
            final Task startedTask = new Task(workStarting.group(1)).startedAt(entry);
            System.out.println("Task '" + workStarting.group(1) + "' started at '" + entry.getCreation() + "' as " + startedTask);
        }

        final Matcher workCompleting = completing.matcher(entry.getBody());
        while (workCompleting.find()){
            final Task completedTask = new Task(workCompleting.group(1)).completeAsOf(entry);
            System.out.println("Task '" + workCompleting.group(1) + "' completed at '" + entry.getCreation() + "' as " + completedTask);
        }
        testEntries.add(entry);
        return entry;
    }

    public PageWrapper<JournalEntry> list(final EntriesQuery query){
        if (testEntries.size() <= query.getStart())
            return PageWrapper.around(Collections.emptyList());
        int limit = query.getSize().orElse(testEntries.size() - query.getStart());
        if (limit > testEntries.size())
            limit = testEntries.size();

        final ArrayList<JournalEntry> orderedTestEntries = IntStream.range(0, testEntries.size())
                .map(i -> (testEntries.size() - 1 - i))
                .mapToObj(testEntries::get)
                .collect(Collectors.toCollection(ArrayList::new));

        final List<JournalEntry> results = orderedTestEntries.subList(
                query.getStart(),
                query.getStart() + limit
        ).stream().filter(e -> {
            return e.getBody().contains(query.getBodyContains());
        }).collect(Collectors.toList());

        final PageWrapper<JournalEntry> page =
                PageWrapper.around(results)
                          .startingAt(query.getStart())
                          .fromPoolOf(testEntries.size());

        if (query.getSize().isPresent()){
            return page.limitedTo(query.getSize().get());
        }

        return page;
    }

    /**
     * @param entryId id of the {@link JournalEntry} to return
     * @return {@link JournalEntry} or throw a {@link ResponseStatusException}/{@link HttpStatus} 404
     */
    public JournalEntry get(final String entryId) {
        final Optional<JournalEntry> first = testEntries.stream().filter(entry -> entry.getId().equals(entryId)).findFirst();

        return first.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Entry not found"
        ));
    }
}
