package com.foreach.across.samples.booking.application.domain.musical;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MusicalId implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;

	public static MusicalId of( String id ) {
		return new MusicalId( id );
	}
}
