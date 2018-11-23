package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonDeserialize(using = MusicalIdDeserializer.class)
@JsonSerialize(using = MusicalIdSerializer.class)
public class MusicalId implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static MusicalId of( String id ) {
		return new MusicalId( id );
	}
}
