package com.rox.journal;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JournalEntriesServiceTest {
    @Test
    void add() {
        final JournalEntry testEntry = new JournalEntry("This is my first test journal entry");
        final JournalEntriesService service = new JournalEntriesService();
        final int countBefore = service.list().size();
        service.add(testEntry);
        final int countAfter = service.list().size();
        final JournalEntry returnedEntry = service.list().get(0);

        assertThat(countBefore == 0);
        assertThat(countAfter == 1);
        assertThat(returnedEntry.equals(testEntry));
    }

    @Test
    void list() {
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        final int countBefore = service.list().size();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final int countAfter = service.list().size();

        assertThat(countBefore == 0);
        assertThat(countAfter == creationCount);
    }

    @Test
    void orderedList() {
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }

        final Map<String, JournalEntry> entries = service.orderedList();

        //TODO how do I test that the date is sequential for i -> i+1
    }
}
