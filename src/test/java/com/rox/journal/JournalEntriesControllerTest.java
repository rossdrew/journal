package com.rox.journal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JournalEntriesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void listingIsEmpty() throws Exception {
        final String url = "http://localhost:" + port + "/entries";

        final ResponseEntity<JournalEntry[]> r =
                restTemplate.getForEntity(
                        url,
                        JournalEntry[].class);
        final JournalEntry[] entries = r.getBody();

        assertThat(entries.length == 0);
    }

    @Test
    public void appendedListingsArePresent() {
        final String appendUrl = "http://localhost:" + port + "/entries/append";
        final String fetchUrl = "http://localhost:" + port + "/entries";

        final int createdEntryCount = 10;

        for (int entryIndex=0; entryIndex<createdEntryCount; entryIndex++){
            final JournalEntry entry = new JournalEntry("This is entry No." + entryIndex, new java.util.Date());
            restTemplate.postForObject(appendUrl, entry, String.class);
        }

        final ResponseEntity<JournalEntry[]> r =
                restTemplate.getForEntity(
                        fetchUrl,
                        JournalEntry[].class);
        final JournalEntry[] entries = r.getBody();

        assertEquals(createdEntryCount, entries.length);
    }
}
