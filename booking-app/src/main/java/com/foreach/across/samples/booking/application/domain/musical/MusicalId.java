package com.foreach.across.samples.booking.application.domain.musical;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicalId
{
	private String id;

	public static MusicalId of( String id ) {
		return new MusicalId( id );
	}
}
