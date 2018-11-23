package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MusicalIdSerializer extends JsonSerializer<MusicalId> {
    @Override
    public void serialize(MusicalId musicalId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(musicalId.getId());
    }
}
