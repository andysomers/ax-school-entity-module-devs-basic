package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.io.Serializable;

@Data
@JsonDeserialize(using = MusicalIdDeserializer.class)
public class MusicalId implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static MusicalId of( String id ) {
		return new MusicalId( id );
	}
}
