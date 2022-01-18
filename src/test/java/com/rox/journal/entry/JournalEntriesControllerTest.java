package com.rox.journal.entry;

import com.rox.journal.PageWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JournalEntriesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listingIsEmpty() {
        final String url = "http://localhost:" + port + "/entries";

        final ResponseEntity<PageWrapper> r =
                restTemplate.getForEntity(url,
                                          PageWrapper.class);
        final PageWrapper entries = r.getBody();

        assertEquals(0, entries.getData().size());
    }

    @Test
    public void appendedListingsArePresent() {
        final String appendUrl = "http://localhost:" + port + "/entries/append";
        final String fetchUrl = "http://localhost:" + port + "/entries";

        final int createdEntryCount = 10;

        for (int entryIndex=0; entryIndex<createdEntryCount; entryIndex++){
            final JournalEntry entry = JournalEntry.create("Entry No." + entryIndex).at(new java.util.Date());
            final JournalEntry newEntry = restTemplate.postForObject(appendUrl, entry, JournalEntry.class);
            System.out.println(newEntry.toString());
        }

        final ResponseEntity<PageWrapper> a =
                restTemplate.getForEntity(
                        fetchUrl,
                        PageWrapper.class);
        final PageWrapper entriesAfter = a.getBody();

        final Object[] ids = entriesAfter.getData().stream().map(e -> ((LinkedHashMap<String, String>) e).get("id")).toArray();
        final HashSet<Object> uniqueIds = new HashSet<>(Arrays.asList(ids));

        assertEquals(createdEntryCount, entriesAfter.getData().size());
        assertEquals(ids.length, uniqueIds.size(), "Entry IDs are not unique");
    }

    //TODO filtering tests
}
