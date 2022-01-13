package com.rox.journal.entry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class JournalEntry {
    private String body;
    private Date creation;
    private String id;

    private final SimpleDateFormat formatter = new SimpleDateFormat("SSS");

    private JournalEntry(final String body, final Date creationTime){
        this.body = body;
        this.creation = creationTime;
        if (creationTime==null) {
            id = "UNKNOWN";
        }else {
            id = String.valueOf((creationTime.getTime() / 1000L)) + "_" + formatter.format(creationTime);
        }
    }

    public static JournalEntry create(final String bodyContent){
        return new JournalEntry(bodyContent, new Date());
    }

    public JournalEntry at(final Date creationTime){
        return new JournalEntry(body, creationTime);
    }

    public String getBody(){
        return body;
    }

    public Date getCreation() {
        return creation;
    }

    public String getId(){
        return this.id;
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
