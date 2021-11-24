package com.rox.journal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void listFilteredOnBodyContent() {
        //TODO Need to be more extensive
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> resultA = service.list(EntriesQuery.all().whereBodyContains(Optional.of("2")));
        final List<JournalEntry> resultB = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Journal")));

        assertTrue(resultA.size() == 1);
        assertTrue(resultB.size() == 10);
    }

    @Test
    void listWithStartPoint() {
        //TODO Need to be more extensive
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> resultA = service.list(EntriesQuery.all().startingAtIndex(Optional.of(1)));
        final List<JournalEntry> resultB = service.list(EntriesQuery.all().startingAtIndex(Optional.of(8)));

        assertTrue(resultA.size() == 9);
        assertTrue(resultB.size() == 2);
    }

    @Test
    void listWithLimit() {
        //TODO Need to be more extensive
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> resultA = service.list(EntriesQuery.all().limitedTo(Optional.empty()));
        final List<JournalEntry> resultB = service.list(EntriesQuery.all().limitedTo(Optional.of(5)));

        assertTrue(resultA.size() == 10);
        assertTrue(resultB.size() == 5);
    }

//    @Test
//    void orderedList() {
//        final int creationCount = 10;
//        final JournalEntriesService service = new JournalEntriesService();
//        for (int i=0; i<creationCount; i++){
//            service.add(new JournalEntry("Journal entry No." + i));
//        }
//
//        final Map<String, JournalEntry> entries = service.orderedEntryMap();
//
//        //TODO how do I test that the date is sequential for i -> i+1
//    }
}
