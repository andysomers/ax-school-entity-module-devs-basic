package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MusicalIdDeserializer extends JsonDeserializer<MusicalId>
{
	@Override
	public MusicalId deserialize( JsonParser jsonParser, DeserializationContext deserializationContext ) throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree( jsonParser );

		return MusicalId.of( node.asText() );
	}
}
