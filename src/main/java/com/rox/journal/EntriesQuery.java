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
        final Optional<Integer> sanitizedIndex = (index.isPresent() && index.get() < 0) ? Optional.of(0) : index;
        return new EntriesQuery(bodyContains, sanitizedIndex.orElse(0), size);
    }

    public EntriesQuery limitedTo(final Optional<Integer> size){
        final Optional<Integer> sanitizedSize = (size.isPresent() && size.get() < 0) ? Optional.of(0) : size;
        return new EntriesQuery(bodyContains, start, sanitizedSize);
    }
}
