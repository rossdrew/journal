package com.rox.journal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JournalEntriesServiceTest {
    JournalEntriesService service = new JournalEntriesService();
    final int testEntryCount = 10;
    final String TEST_ENTRY_PREFIX = "Test Entry No.";

    @BeforeEach
    void setup(){
        this.service = new JournalEntriesService();
    }

    void createTestEntries(){
        for (int entryIndex = 0; entryIndex< 10; entryIndex++){
            service.append(new JournalEntry(TEST_ENTRY_PREFIX + entryIndex));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread sleep failed, who cares?");
            }
        }
    }

    @Test
    void appendSimpleEntry() {
        final JournalEntry testEntry = new JournalEntry("This is my first test journal entry");
        final PageWrapper<JournalEntry> resultBefore = service.list(EntriesQuery.all());
        service.append(testEntry);
        final PageWrapper<JournalEntry> resultAfter = service.list(EntriesQuery.all());

        assertEquals(0, resultBefore.getData().size());
        assertFalse(resultBefore.getSize().isPresent());
        assertEquals(1, resultAfter.getData().size());
        assertFalse(resultBefore.getSize().isPresent());
        assertTrue(resultAfter.getData().get(0).equals(testEntry));
        assertEquals(resultAfter.getData().get(0),testEntry);
    }

    @Test
    void unfilteredList() {
        final int countBefore = service.list(EntriesQuery.all()).getData().size();
        createTestEntries();
        final int countAfter = service.list(EntriesQuery.all()).getData().size();

        assertEquals(0, countBefore);
        assertEquals(testEntryCount, countAfter);
    }

    @Test
    void listWithBodyContentFilterUnspecified() {
        createTestEntries();
        final PageWrapper<JournalEntry> unspecified = service.list(EntriesQuery.all().whereBodyContains(Optional.empty()));
        assertEquals(testEntryCount, unspecified.getData().size());
    }

    @Test
    void listWithBodyContentFilterEmpty() {
        createTestEntries();
        final PageWrapper<JournalEntry> empty = service.list(EntriesQuery.all().whereBodyContains(Optional.of("")));
        assertEquals(testEntryCount, empty.getData().size());
    }

    @Test
    void listWithBodyContentFilterApplyingToOneEntry() {
        createTestEntries();
        final PageWrapper<JournalEntry> appliesToOne = service.list(EntriesQuery.all().whereBodyContains(Optional.of("2")));
        assertEquals(1, appliesToOne.getData().size());
    }

    @Test
    void listWithBodyContentFilterThatAppliesToAllEntries() {
        createTestEntries();
        final PageWrapper<JournalEntry> appliesToAll = service.list(EntriesQuery.all().whereBodyContains(Optional.of(TEST_ENTRY_PREFIX.substring(3,6))));
        assertEquals(testEntryCount, appliesToAll.getData().size());
    }

    @Test
    void listWithBodyContentFilterThatAppliesToNoEntries() {
        createTestEntries();
        final PageWrapper<JournalEntry> appliesToNone = service.list(EntriesQuery.all().whereBodyContains(Optional.of("Locke Lamora")));
        assertEquals(0, appliesToNone.getData().size());
    }

    @Test
    void listWithSubZeroStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> subZero = service.list(EntriesQuery.all().startingAtIndex(Optional.of(-1)));
        assertEquals(testEntryCount, subZero.getData().size(),"Invalid start point should default to start of data");
        assertEquals(TEST_ENTRY_PREFIX+"0", subZero.getData().get(0).getBody());
    }

    @Test
    void listWithUnspecifiedStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> unspecified = service.list(EntriesQuery.all().startingAtIndex(Optional.empty()));
        assertEquals(testEntryCount, unspecified.getData().size(),"No specified start point should default to start of data");
        assertEquals(TEST_ENTRY_PREFIX+"0", unspecified.getData().get(0).getBody());
    }

    @Test
    void listWithZeroStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> zeroSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(0)));
        assertEquals(testEntryCount, zeroSpecified.getData().size(), "Start point of 0 should be the start of data");
        assertEquals(TEST_ENTRY_PREFIX+"0", zeroSpecified.getData().get(0).getBody());
    }

    @Test
    void listWithGreaterThanZeroStartIndex(){
        createTestEntries();
        final int decrement = 1;
        final PageWrapper<JournalEntry> oneSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(1)));
        assertEquals(testEntryCount - decrement, oneSpecified.getData().size(), "Starting from "+decrement+" should return max-"+decrement+" entries");
        assertEquals(TEST_ENTRY_PREFIX+decrement, oneSpecified.getData().get(0).getBody());
    }

    @Test
    void listWithLessThanMaxStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> subMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount - 2)));
        assertEquals(2, subMaxSpecified.getData().size(), "Some number less than max should return max-n entries");
    }

    @Test
    void listWithMaxStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> maxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount)));
        //XXX Should we return error or empty list?
        assertEquals(0, maxSpecified.getData().size(), "Starting at max should return no entries");
    }

    @Test
    void listWithGreaterThanMaxStartIndex(){
        createTestEntries();
        final PageWrapper<JournalEntry> superMaxSpecified = service.list(EntriesQuery.all().startingAtIndex(Optional.of(testEntryCount + 1)));
        //XXX Should we return error or empty list?
        assertEquals(0, superMaxSpecified.getData().size(), "Starting higher than should return no entries");
    }

    @Test
    void listWithSubZeroLimit() {
        createTestEntries();
        final PageWrapper<JournalEntry> subZero = service.list(EntriesQuery.all().limitedTo(Optional.of(-1)));
        assertEquals(0, subZero.getData().size(), "With invalid limit specified, should default to none");
    }

    @Test
    void listWithZeroLimit() {
        createTestEntries();
        final PageWrapper<JournalEntry> zero = service.list(EntriesQuery.all().limitedTo(Optional.of(0)));
        assertEquals(0, zero.getData().size(), "With invalid limit specified, should default to none");
    }

    @Test
    void listWithUnspecifiedLimit() {
        createTestEntries();
        final PageWrapper<JournalEntry> unspecified = service.list(EntriesQuery.all().limitedTo(Optional.empty()));
        assertEquals(10, unspecified.getData().size(), "With no limit specified, should default to all");
    }

    @Test
    void listWithSpecifiedLimit() {
        createTestEntries();
        final PageWrapper<JournalEntry> validNumber = service.list(EntriesQuery.all().limitedTo(Optional.of(5)));
        assertEquals(5, validNumber.getData().size(), "With limit specified, that number should be returned");
    }

    @Test
    void listWithMaxLimit() {
        createTestEntries();
        final PageWrapper<JournalEntry> max = service.list(EntriesQuery.all().limitedTo(Optional.of(testEntryCount)));
        assertEquals(10, max.getData().size(), "Limit equal to number of entries should return all entries");
    }

    @Test
    void listWithSuperMaxLimit() {
        createTestEntries();

        final PageWrapper<JournalEntry> superMax = service.list(EntriesQuery.all().limitedTo(Optional.of(testEntryCount +1)));
        assertEquals(10, superMax.getData().size(), "Limit greater than number of entries should return all entries");
    }

    @Test
    void listSpecifyingRangeOfZeroSize() {
        createTestEntries();
        final PageWrapper<JournalEntry> superMax = service.list(
                EntriesQuery.all()
                        .startingAtIndex(Optional.of(4))
                        .limitedTo(Optional.of(0)));
        assertEquals(0, superMax.getData().size(), "Limit of zero should always return 0, no matter the index");
    }

    @Test
    void listSpecifyingRangeOfNonZeroSize() {
        createTestEntries();
        final int startIndex = 4;
        final int size = 1;
        final PageWrapper<JournalEntry> range = service.list(
                EntriesQuery.all()
                        .startingAtIndex(Optional.of(startIndex))
                        .limitedTo(Optional.of(size)));
        assertEquals(size, range.getData().size(), "Range of " + size + " should return " + size + " item at the correct index (" + startIndex + ")");
        assertEquals(TEST_ENTRY_PREFIX+startIndex, range.getData().get(0).getBody());
    }

    @Test
    void listWithAllFiltersTurnedOn() {
        createTestEntries();
        final int startIndex = 5;
        final int size = 4;
        final String searchText = "8";
        final PageWrapper<JournalEntry> uberFiltered = service.list(
                EntriesQuery.all()
                        .startingAtIndex(Optional.of(startIndex))
                        .limitedTo(Optional.of(size))
                        .whereBodyContains(Optional.of(searchText)));
        assertEquals(1, uberFiltered.getData().size(), "Expected only 1 item between " + startIndex + " and " + (startIndex+size) + " containing '" + searchText + "'");
        assertEquals(TEST_ENTRY_PREFIX + searchText, uberFiltered.getData().get(0).getBody());
    }
}
