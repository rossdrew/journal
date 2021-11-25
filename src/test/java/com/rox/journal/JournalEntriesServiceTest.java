package com.rox.journal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals(0, countBefore);
        assertEquals(1, countAfter);
        assertTrue(returnedEntry.equals(testEntry));
        assertEquals(returnedEntry,testEntry);
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

        assertEquals(0, countBefore);
        assertEquals(creationCount, countAfter);
    }

    @Test
    void listFilteredOnBodyContent() {
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().whereBodyContains(Optional.empty()));
        final List<JournalEntry> empty = service.list(EntriesQuery.all().whereBodyContains(Optional.of("")));
        final List<JournalEntry> appliesToOne = service.list(EntriesQuery.all().whereBodyContains(Optional.of("2")));
        final List<JournalEntry> appliesToAll = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Journal")));
        final List<JournalEntry> appliesToNone = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Locke Lamora")));

        assertEquals(creationCount, unspecified.size());
        assertEquals(creationCount, empty.size());
        assertEquals(1, appliesToOne.size());
        assertEquals(creationCount, appliesToAll.size());
        assertEquals(0, appliesToNone.size());
    }

    @Test
    void listWithStartPoint() {
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> subZero = service.list(EntriesQuery.all().startingAtIndex(Optional.of(-1)));

        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().startingAtIndex(Optional.empty()));
        final List<JournalEntry> zeroSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(0)));

        final List<JournalEntry> oneSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(1)));
        final List<JournalEntry> subMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(creationCount-2)));

        final List<JournalEntry> maxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(creationCount)));
        final List<JournalEntry> superMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(creationCount+1)));

        assertEquals(creationCount, subZero.size(),"Invalid start point should default to start of data");
        assertEquals(creationCount, unspecified.size(),"No specified start point should default to start of data");
        assertEquals(creationCount, zeroSpecified.size(), "Start point of 0 should be the start of data");
        assertEquals(creationCount-1, oneSpecified.size(), "Starting from 1 should return max-1 entries");
        assertEquals(2, subMaxSpecified.size(), "Some number less than max should return max-n entries");
        //XXX Should we return errors or empty lists?
        assertEquals(0, maxSpecified.size(), "Starting at max should return no entries");
        assertEquals(0, superMaxSpecified.size(), "Starting higher than should return no entries");
    }

    @Test
    void listWithLimit() {
        final int creationCount = 10;
        final JournalEntriesService service = new JournalEntriesService();
        for (int i=0; i<creationCount; i++){
            service.add(new JournalEntry("Journal entry No." + i));
        }
        final List<JournalEntry> subZero = service.list(EntriesQuery.all().limitedTo(Optional.of(-1)));
        final List<JournalEntry> zero = service.list(EntriesQuery.all().limitedTo(Optional.of(0)));
        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().limitedTo(Optional.empty()));
        final List<JournalEntry> validNumber = service.list(EntriesQuery.all().limitedTo(Optional.of(5)));
        final List<JournalEntry> max = service.list(EntriesQuery.all().limitedTo(Optional.of(creationCount)));
        final List<JournalEntry> superMax = service.list(EntriesQuery.all().limitedTo(Optional.of(creationCount+1)));

        assertEquals(0, subZero.size(), "With invalid limit specified, should default to none");
        assertEquals(0, zero.size(), "With invalid limit specified, should default to none");
        assertEquals(10, unspecified.size(), "With no limit specified, should default to all");
        assertEquals(5, validNumber.size(), "With limit specified, that number should be returned");
        assertEquals(10, max.size(), "Limit equal to number of entries should return all entries");
        assertEquals(10, superMax.size(), "Limit greater than number of entries should return all entries");
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
