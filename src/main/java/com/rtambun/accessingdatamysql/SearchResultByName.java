package com.rtambun.accessingdatamysql;

//@JsonSerialize(using = SearchResultByNameSerializer.class)
public class SearchResultByName {
    private int exactMatchCount;

    private int matchCount;

    public void setExactMatchCount(int exactMatchCount) {
        this.exactMatchCount = exactMatchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getExactMatchCount() {
        return exactMatchCount;
    }

    public int getMatchCount() {
        return matchCount;
    }
}
