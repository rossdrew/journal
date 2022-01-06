package com.rox.journal;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntriesQueryTest {

    @Test
    public void testDefaultQuery(){
        final EntriesQuery defaultQuery = EntriesQuery.all();

        assertEquals("", defaultQuery.getBodyContains());
        assertEquals(0, defaultQuery.getStart());
        assertEquals(Optional.empty(), defaultQuery.getSize());
    }

    @Test
    public void testQueryWithBodyFilter(){
        final EntriesQuery defaultQuery = EntriesQuery
                .all()
                .whereBodyContains(Optional.of("Ross"));

        assertEquals("Ross", defaultQuery.getBodyContains());
        assertEquals(0, defaultQuery.getStart());
        assertEquals(Optional.empty(), defaultQuery.getSize());
    }

    @Test
    public void testQueryWithStartIndexDefined(){
        final EntriesQuery defaultQuery = EntriesQuery
                .all()
                .startingAtIndex(Optional.of(23));

        assertEquals("", defaultQuery.getBodyContains());
        assertEquals(23, defaultQuery.getStart());
        assertEquals(Optional.empty(), defaultQuery.getSize());
    }

    @Test
    public void testQueryWithLimitDefined(){
        final EntriesQuery defaultQuery = EntriesQuery
                .all()
                .limitedTo(Optional.of(101));

        assertEquals("", defaultQuery.getBodyContains());
        assertEquals(0, defaultQuery.getStart());
        assertEquals(Optional.of(101), defaultQuery.getSize());
    }

    @Test
    public void testQueryWithRangeDefined(){
        final EntriesQuery defaultQuery = EntriesQuery
                .all()
                .startingAtIndex(Optional.of(10))
                .limitedTo(Optional.of(101));

        assertEquals("", defaultQuery.getBodyContains());
        assertEquals(10, defaultQuery.getStart());
        assertEquals(Optional.of(101), defaultQuery.getSize());
    }

    @Test
    public void testQueryWithRangeAndBodyFilterDefined(){
        final EntriesQuery defaultQuery = EntriesQuery
                .all()
                .whereBodyContains(Optional.of("Locke Lamora"))
                .startingAtIndex(Optional.of(10))
                .limitedTo(Optional.of(101));

        assertEquals("Locke Lamora", defaultQuery.getBodyContains());
        assertEquals(10, defaultQuery.getStart());
        assertEquals(Optional.of(101), defaultQuery.getSize());
    }

}
