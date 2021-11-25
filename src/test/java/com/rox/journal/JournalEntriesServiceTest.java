package com.rox.journal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JournalEntriesServiceTest {
    JournalEntriesService service = new JournalEntriesService();
    final int testEntryCount = 10;

    @BeforeEach
    void setup(){
        this.service = new JournalEntriesService();
    }

    void createTestEntries(){
        for (int entryIndex = 0; entryIndex< 10; entryIndex++){
            service.append(new JournalEntry("Journal entry No." + entryIndex));
        }
    }

    @Test
    void appendSimpleEntry() {
        final JournalEntry testEntry = new JournalEntry("This is my first test journal entry");
        final int countBefore = service.list().size();
        service.append(testEntry);
        final int countAfter = service.list().size();
        final JournalEntry returnedEntry = service.list().get(0);

        assertEquals(0, countBefore);
        assertEquals(1, countAfter);
        assertTrue(returnedEntry.equals(testEntry));
        assertEquals(returnedEntry,testEntry);
    }

    @Test
    void unfilteredList() {
        final int countBefore = service.list().size();
        createTestEntries();
        final int countAfter = service.list().size();

        assertEquals(0, countBefore);
        assertEquals(testEntryCount, countAfter);
    }

    @Test
    void listWithBodyContentFilterUnspecified() {
        createTestEntries();
        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().whereBodyContains(Optional.empty()));
        assertEquals(testEntryCount, unspecified.size());
    }

    @Test
    void listWithBodyContentFilterEmpty() {
        createTestEntries();
        final List<JournalEntry> empty = service.list(EntriesQuery.all().whereBodyContains(Optional.of("")));
        assertEquals(testEntryCount, empty.size());
    }

    @Test
    void listWithBodyContentFilterApplyingToOneEntry() {
        createTestEntries();
        final List<JournalEntry> appliesToOne = service.list(EntriesQuery.all().whereBodyContains(Optional.of("2")));
        assertEquals(1, appliesToOne.size());
    }

    @Test
    void listWithBodyContentFilterThatAppliesToAllEntries() {
        createTestEntries();
        final List<JournalEntry> appliesToAll = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Journal")));
        assertEquals(testEntryCount, appliesToAll.size());
    }

    @Test
    void listWithBodyContentFilterThatAppliesToNoEntries() {
        createTestEntries();
        final List<JournalEntry> appliesToNone = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Locke Lamora")));
        assertEquals(0, appliesToNone.size());
    }

    @Test
    void listWithSubZeroStartIndex(){
        createTestEntries();
        final List<JournalEntry> subZero = service.list(EntriesQuery.all().startingAtIndex(Optional.of(-1)));
        assertEquals(testEntryCount, subZero.size(),"Invalid start point should default to start of data");
    }

    @Test
    void listWithUnspecifiedStartIndex(){
        createTestEntries();
        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().startingAtIndex(Optional.empty()));
        assertEquals(testEntryCount, unspecified.size(),"No specified start point should default to start of data");
    }

    @Test
    void listWithZeroStartIndex(){
        createTestEntries();
        final List<JournalEntry> zeroSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(0)));
        assertEquals(testEntryCount, zeroSpecified.size(), "Start point of 0 should be the start of data");
    }

    @Test
    void listWithGreaterThanZeroStartIndex(){
        createTestEntries();
        final List<JournalEntry> oneSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(1)));
        assertEquals(testEntryCount -1, oneSpecified.size(), "Starting from 1 should return max-1 entries");
    }

    @Test
    void listWithLessThanMaxStartIndex(){
        createTestEntries();
        final List<JournalEntry> subMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount -2)));
        assertEquals(2, subMaxSpecified.size(), "Some number less than max should return max-n entries");
    }

    @Test
    void listWithMaxStartIndex(){
        createTestEntries();
        final List<JournalEntry> maxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount)));
        //XXX Should we return error or empty list?
        assertEquals(0, maxSpecified.size(), "Starting at max should return no entries");
    }

    @Test
    void listWithGreaterThanMaxStartIndex(){
        createTestEntries();
        final List<JournalEntry> superMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount +1)));
        //XXX Should we return error or empty list?
        assertEquals(0, superMaxSpecified.size(), "Starting higher than should return no entries");
    }

    @Test
    void listWithSubZeroLimit() {
        createTestEntries();
        final List<JournalEntry> subZero = service.list(EntriesQuery.all().limitedTo(Optional.of(-1)));
        assertEquals(0, subZero.size(), "With invalid limit specified, should default to none");
    }

    @Test
    void listWithZeroLimit() {
        createTestEntries();
        final List<JournalEntry> zero = service.list(EntriesQuery.all().limitedTo(Optional.of(0)));
        assertEquals(0, zero.size(), "With invalid limit specified, should default to none");
    }

    @Test
    void listWithUnspecifiedLimit() {
        createTestEntries();
        final List<JournalEntry> unspecified = service.list(EntriesQuery.all().limitedTo(Optional.empty()));
        assertEquals(10, unspecified.size(), "With no limit specified, should default to all");
    }

    @Test
    void listWithSpecifiedLimit() {
        createTestEntries();
        final List<JournalEntry> validNumber = service.list(EntriesQuery.all().limitedTo(Optional.of(5)));
        assertEquals(5, validNumber.size(), "With limit specified, that number should be returned");
    }

    @Test
    void listWithMaxLimit() {
        createTestEntries();
        final List<JournalEntry> max = service.list(EntriesQuery.all().limitedTo(Optional.of(testEntryCount)));
        assertEquals(10, max.size(), "Limit equal to number of entries should return all entries");
    }

    @Test
    void listWithSuperMaxLimit() {
        createTestEntries();

        final List<JournalEntry> superMax = service.list(EntriesQuery.all().limitedTo(Optional.of(testEntryCount +1)));
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
