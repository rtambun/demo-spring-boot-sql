package com.rtambun.accessingdatamysql.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rtambun.accessingdatamysql.model.LocationInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonSerialize(using = LocationInfoByLocationSerializer.class)
public class LocationInfoByLocation {

    private String location;

    List<LocationInfo> result;

    public LocationInfoByLocation() {
        result = new ArrayList<LocationInfo>();
    }

    public void addResult(LocationInfo locationInfo) {
        result.add(locationInfo);
    }

}
