package com.foreach.across.samples.booking.application.domain.show.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.foreach.across.samples.booking.application.domain.show.ShowId;

import java.io.IOException;

public class ShowIdSerializer extends JsonSerializer<ShowId>
{
	@Override
	public void serialize( ShowId value, JsonGenerator gen, SerializerProvider serializers ) throws IOException, JsonProcessingException {
		gen.writeString( value.getId() );
	}
}
