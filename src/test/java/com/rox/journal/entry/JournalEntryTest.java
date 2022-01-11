package com.rox.journal.entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JournalEntryTest {
    final Date testDate = new Date();

    @BeforeAll
    public static void setup(){
        try {
            //Make sure the test date is distinguishable from other dates created during test
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMinimalCreation(){
        final String testContent = "This is a test";
        final JournalEntry entry = new JournalEntry(testContent);

        assertEquals(testContent, entry.getBody());
        assertNull(entry.getCreation());
    }

    @Test
    public void testFullCreation(){
        final String testContent = "This is a test";
        final Date testDate = new Date();
        final JournalEntry entry = new JournalEntry(testContent, testDate);

        assertEquals(testContent, entry.getBody());
        assertEquals(testDate, entry.getCreation());
    }

    @Test
    public void testEquality(){
        final String testContent = "This is a test";
        final Date testDate = new Date();
        final JournalEntry entryA = new JournalEntry(testContent, testDate);
        final JournalEntry entryB = new JournalEntry(testContent, testDate);

        assertEquals(entryA, entryB);
        assertEquals(entryB, entryA);
        assertThat(entryA.equals(entryB));
        assertThat(entryB.equals(entryA));
    }

    @Test
    public void testInequality(){
        final JournalEntry entryA = new JournalEntry("This is a test", testDate);
        final JournalEntry differentContentEntry = new JournalEntry("This is not the same", testDate);
        final JournalEntry differentDateEntry = new JournalEntry("This is a test", new Date());
        final JournalEntry differentContentAndDateEntry = new JournalEntry("This is not the same", new Date());

        assertNotEquals(entryA, differentContentEntry);
        assertNotEquals(differentContentEntry, entryA);
        assertThat(!entryA.equals(differentContentEntry));
        assertThat(!differentContentEntry.equals(entryA));

        assertNotEquals(entryA, differentDateEntry);
        assertNotEquals(differentDateEntry, entryA);
        assertThat(!entryA.equals(differentDateEntry));
        assertThat(!differentDateEntry.equals(entryA));

        assertNotEquals(entryA, differentContentAndDateEntry);
        assertNotEquals(differentContentAndDateEntry, entryA);
        assertThat(!entryA.equals(differentContentAndDateEntry));
        assertThat(!differentContentAndDateEntry.equals(entryA));
    }
}
