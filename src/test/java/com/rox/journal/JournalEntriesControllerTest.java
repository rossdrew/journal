package com.rox.journal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

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
                restTemplate.getForEntity(
                        url,
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
            final JournalEntry entry = new JournalEntry("Entry No." + entryIndex, new java.util.Date());
            restTemplate.postForObject(appendUrl, entry, String.class);
        }

        final ResponseEntity<PageWrapper> a =
                restTemplate.getForEntity(
                        fetchUrl,
                        PageWrapper.class);
        final PageWrapper entriesAfter = a.getBody();

        assertEquals(entriesAfter.getData().size(), createdEntryCount);
    }

    //TODO filtering tests
}
