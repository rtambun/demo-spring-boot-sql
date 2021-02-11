package com.rtambun.accessingdatamysql.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rtambun.accessingdatamysql.model.LocationInfo;

import java.io.IOException;

public class LocationInfoByLocationSerializer extends StdSerializer<LocationInfoByLocation> {

    public LocationInfoByLocationSerializer() {
        this(null);
    }

    public LocationInfoByLocationSerializer(Class<LocationInfoByLocation> locationInfoByLocationClass) {
        super(locationInfoByLocationClass);
    }

    @Override
    public void serialize(LocationInfoByLocation value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName(value.getLocation());
        gen.writeStartArray();
        for (LocationInfo locationInfo : value.getResult()) {
            gen.writeStartObject();
            gen.writeFieldName("id");
            gen.writeRawValue(Integer.toString(locationInfo.getId()));
            gen.writeFieldName("name");
            gen.writeRawValue(locationInfo.getName());
            gen.writeFieldName("Geopoint");
            gen.writeRawValue(locationInfo.getLatitude() + "," + locationInfo.getLongitude());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }


}
