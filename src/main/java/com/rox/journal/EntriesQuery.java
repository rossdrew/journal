package com.rox.journal;

import java.util.Optional;

public class EntriesQuery {
    private final String bodyContains;
    private final Integer start;
    private final Optional<Integer> size;

    private EntriesQuery(final String bodyContains,
                         final Integer start,
                         final Optional<Integer> size){
        this.bodyContains = bodyContains;
        this.start = start;
        this.size = size;
    }

    public String getBodyContains() {
        return bodyContains;
    }

    public Integer getStart(){
        return start;
    }

    public Optional<Integer> getSize(){
        return size;
    }

    public static EntriesQuery all(){
        return new EntriesQuery("",0, Optional.empty());
    }

    public EntriesQuery whereBodyContains(final Optional<String> containsText){
        return new EntriesQuery(containsText.orElse(""), start, size);
    }

    public EntriesQuery startingAtIndex(final Optional<Integer> index){
        return new EntriesQuery(bodyContains, index.orElse(0), size);
    }

    public EntriesQuery limitedTo(final Optional<Integer> size){
        return new EntriesQuery(bodyContains, start, size);
    }
}
