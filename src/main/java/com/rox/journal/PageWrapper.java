package com.rox.journal;

import java.util.List;
import java.util.Optional;

/**
 * A wrapper around a generalised {@link DataType} providing paging header describing it's part in the larger
 * pool of data from which it was extracted
 *
 * @param <DataType> the data type being wrapped
 */
public class PageWrapper<DataType> {
    private final Optional<Integer> poolSize;
    private final Optional<Integer> upperLimit;
    private final int startIndex;
    private final List<DataType> data;

    private PageWrapper(final Optional<Integer> poolSize,
                        final Optional<Integer> upperLimit,
                        final int startIndex,
                        final List<DataType> data) {
        this.poolSize = poolSize;
        this.upperLimit = upperLimit;
        this.startIndex = startIndex;
        this.data = data;
    }

    /**
     * @param data List to wrap in paging information
     * @param <DataType> of the list
     * @return a new {@link PageWrapper} of the given data type, containing the given data
     */
    public static <DataType> PageWrapper<DataType> around(final List<DataType> data){
        return new PageWrapper<>(Optional.empty(), Optional.empty(), 0, data);
    }

    /**
     * @param index in a larger pool from which the data was extracted
     * @param <DataType> of the given data
     * @return a new {@link PageWrapper} with the attributes of the source {@link PageWrapper} with index set to given value
     */
    public <DataType> PageWrapper<DataType> startingAt(final int index){
        return new PageWrapper(Optional.empty(), Optional.empty(), index, data);
    }

    /**
     * @param count the max number of items requested from a larger pool
     * @param <DataType> of the given data
     * @return a new {@link PageWrapper} with the attributes of the source {@link PageWrapper} with limit set to given value
     */
    public <DataType> PageWrapper<DataType> limitedTo(final int count){
        if (data.size() > count)
            throw new RuntimeException("Data is larger than the defined size limit we tried to set"); //XXX Custom exception required

        return new PageWrapper(Optional.empty(), Optional.of(Math.max(count, 0)), startIndex, data);
    }

    /**
     * @param count the number of items from which this data was extracted
     * @param <DataType> of the given data
     * @return a new {@link PageWrapper} with the attributes of the source {@link PageWrapper} with count set to given value
     */
    public <DataType> PageWrapper<DataType> fromPoolOf(final int count){
        return new PageWrapper(Optional.of(Math.max(count, 0)), upperLimit, startIndex, data);
    }

    public Optional<Integer> getPoolSize() {
        return poolSize;
    }

    public Optional<Integer> getLimit() {
        return upperLimit;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public List<DataType> getData() {
        return data;
    }
}
