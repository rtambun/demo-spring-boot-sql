package com.rtambun.accessingdatamysql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SearchResultByLocationSerializer extends StdSerializer<SearchResultByLocation> {

    public SearchResultByLocationSerializer() {
        this(null);
    }

    public SearchResultByLocationSerializer(Class<SearchResultByLocation> result) {
        super(result);
    }


    @Override
    public void serialize(SearchResultByLocation value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName(value.getLocationQuery());
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
