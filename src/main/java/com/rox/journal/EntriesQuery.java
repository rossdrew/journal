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

    /**
     * @return a new permissive query with a defined starting index of 0, no filtering and no limit.
     */
    public static EntriesQuery all(){
        return new EntriesQuery("",0, Optional.empty());
    }

    /**
     * Create a new query with the constraints of the original and the containsText constraint added.
     * If containsText constraint exists on the original it will be replaced with the defined one.
     *
     * @param containsText a {@link String} which we expect to filter out query results which do not contain it
     * @return A new query based on the one called, with the provided containsText constraint.
     */
    public EntriesQuery whereBodyContains(final Optional<String> containsText){
        return new EntriesQuery(containsText.orElse(""), start, size);
    }

    /**
     * Create a new query with the constraints of the original and the startingIndex constraint added.
     * If startingIndex constraint exists on the original it will be replaced with the defined one.
     *
     * @param index an {@link Optional} {@link Integer} defining from which point in the data pool we want to draw data
     * @return A new query based on the one called, with the provided startingIndex constraint.
     */
    public EntriesQuery startingAtIndex(final Optional<Integer> index){
        final Optional<Integer> sanitizedIndex = (index.isPresent() && index.get() < 0) ? Optional.of(0) : index;
        return new EntriesQuery(bodyContains, sanitizedIndex.orElse(0), size);
    }

    /**
     * Create a new query with the constraints of the original and the limit constraint added.
     * If limit constraint exists on the original it will be replaced with the defined one.
     *
     * @param size an {@link Optional} {@link Integer} defining the maximum number (limit) of results to fetch
     * @return A new query based on the one called, with the provided limit constraint.
     */
    public EntriesQuery limitedTo(final Optional<Integer> size){
        final Optional<Integer> sanitizedSize = (size.isPresent() && size.get() < 0) ? Optional.of(0) : size;
        return new EntriesQuery(bodyContains, start, sanitizedSize);
    }
}
