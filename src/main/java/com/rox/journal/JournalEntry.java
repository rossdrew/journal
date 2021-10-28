package com.rox.journal;

import java.util.Date;
import java.util.Objects;

public class JournalEntry {
    private String body;
    private Date creation;

    /**
     * Create an undated JournalEntry
     */
    public JournalEntry(final String body){
        this.body = body;
        this.creation = null;
    }

    /**
     * Create a dated journal entry
     */
    public JournalEntry(final String body, final Date creationTime){
        this.body = body;
        this.creation = creationTime;
    }

    public String getBody(){
        return body;
    }

    public void setBody(final String body){
        this.body = body;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalEntry that = (JournalEntry) o;
        return Objects.equals(body, that.body) && Objects.equals(creation, that.creation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, creation);
    }
}
