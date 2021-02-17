package com.rtambun.accessingdatamysql.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter @Getter
@Table(name = "locationinfo")
public class LocationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String location;

    private double latitude;

    private double longitude;

    public LocationInfo() {}

    public LocationInfo (String name, String location, double latitude, double longitude) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
