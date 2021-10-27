package com.rox.journal;

import java.util.Date;

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
}
