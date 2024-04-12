package com.rox.journal.entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JournalEntryTest {
    final Date testDate = new GregorianCalendar(2015, Calendar.OCTOBER, 21).getTime();

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
        final JournalEntry entry = JournalEntry.create(testContent);

        assertEquals(testContent, entry.getBody());
        assertNotNull(entry.getCreation());
    }

    @Test
    public void testFullCreation(){
        final String testContent = "This is a test";
        final Date testDate = new Date();
        final JournalEntry entry = JournalEntry.create(testContent).at(testDate);

        assertEquals(testContent, entry.getBody());
        assertEquals(testDate, entry.getCreation());
    }

    @Test
    public void testEquality(){
        final String testContent = "This is a test";
        final Date testDate = new Date();
        final JournalEntry entryA = JournalEntry.create(testContent).at(testDate);
        final JournalEntry entryB = JournalEntry.create(testContent).at(testDate);

        assertEquals(entryA, entryB);
        assertEquals(entryB, entryA);
        assertThat(entryA.equals(entryB));
        assertThat(entryB.equals(entryA));
    }

    @Test
    public void testInequality(){
        final JournalEntry entryA = JournalEntry.create("Marty Arrives in 2015").at(testDate);
        final JournalEntry differentContentEntry = JournalEntry.create("Jaws 19 Released").at(testDate);
        final JournalEntry differentDateEntry = JournalEntry.create("This is a test").at(new Date());
        final JournalEntry differentContentAndDateEntry = JournalEntry.create("This is not the same").at(new Date());

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
