package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Serializable;

@JsonDeserialize(using = MusicalId.Deserializer.class)
@JsonSerialize(using = MusicalId.Serializer.class)
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicalId implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Getter(AccessLevel.PACKAGE)
	private final String id;

	@Override
	public String toString() {
		return id;
	}

	public static MusicalId of( String id ) {
		return id != null ? new MusicalId( id ) : null;
	}

	/**
	 * Custom deserializer for Jackson.
	 */
	static class Deserializer extends JsonDeserializer
	{
		@Override
		public MusicalId deserialize( JsonParser jsonParser, DeserializationContext deserializationContext ) throws IOException, JsonProcessingException {
			ObjectCodec oc = jsonParser.getCodec();
			JsonNode node = oc.readTree( jsonParser );

			return of( node.asText() );
		}
	}

	/**
	 * Custom serializer for Jackson.
	 */
	static class Serializer extends JsonSerializer<MusicalId>
	{
		@Override
		public void serialize( MusicalId musicalId,
		                       JsonGenerator jsonGenerator,
		                       SerializerProvider serializerProvider ) throws IOException, JsonProcessingException {
			jsonGenerator.writeString( musicalId.getId() );
		}
	}
}

