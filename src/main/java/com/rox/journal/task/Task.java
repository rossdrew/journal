package com.rox.journal.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rox.journal.entry.JournalEntry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Task {
    /** The relationship of a given {@link Task} to the linked entity ({@link JournalEntry}) */
    public enum TaskRelationship {
        START,
        COMPLETE,
        LINK
    }

    private String id;
    @JsonDeserialize(keyUsing = TaskToEntryMapSerializer.class)
    private Map<JournalEntry, TaskRelationship> relationships = Collections.emptyMap();

    public String getId() {
        return id;
    }

    public Map<JournalEntry, TaskRelationship> getRelationships() {
        return relationships;
    }

    public Task(final String id){
        this.id = id;
    }

    private Task(final String id, final Map<JournalEntry, TaskRelationship> relationships){
        this.id = id;
        this.relationships = relationships;
    }

    public Task startedAt(final JournalEntry startEntry){
        return new Task(id, mapWrap(startEntry, TaskRelationship.START));
    }

    public Task completeAsOf(final JournalEntry completeEntry){
        return new Task(id, mapWrap(completeEntry, TaskRelationship.COMPLETE));
    }

    public Task linkedTo(final JournalEntry linkedEntry){
        return new Task(id, mapWrap(linkedEntry, TaskRelationship.LINK));
    }

    private Map<JournalEntry, TaskRelationship> mapWrap(final JournalEntry entry,
                                                        final TaskRelationship relationship){
        final Map<JournalEntry, TaskRelationship> relationships = new HashMap<>(this.relationships);
        relationships.put(entry, relationship);
        return relationships;
    }
}
