package com.rtambun.accessingdatamysql;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(using = SearchResultByLocationSerializer.class)
public class SearchResultByLocation {

    private String locationQuery;

    private List<LocationInfo> result;

    public void setLocationQuery(String location) {
        this.locationQuery = location;
    }

    public void setResult(List<LocationInfo> result) {
        this.result = result;
    }

    public String getLocationQuery() {
        return locationQuery;
    }

    public List<LocationInfo> getResult() {
        return result;
    }
}
