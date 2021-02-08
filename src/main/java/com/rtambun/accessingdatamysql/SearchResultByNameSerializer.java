package com.rtambun.accessingdatamysql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SearchResultByNameSerializer extends StdSerializer<SearchResultByName> {

    public SearchResultByNameSerializer() {
        this(null);
    }

    public SearchResultByNameSerializer(Class<SearchResultByName> result) {
        super(result);
    }

    @Override
    public void serialize(SearchResultByName value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        gen.writeStartObject();
        gen.writeRaw("exactMatchCount:" + value.getExactMatchCount());
        gen.writeRaw("matchCount" + value.getMatchCount());
        gen.writeStartObject();
    }
}
