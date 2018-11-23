package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonDeserialize(using = MusicalIdDeserializer.class)
@JsonSerialize(using = MusicalIdSerializer.class)
public class MusicalId
{
	private final String id;

	public static MusicalId of( String id ) {
		return new MusicalId( id );
	}
}
