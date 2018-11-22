package com.foreach.across.samples.booking.application.domain.musical;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicalId
{
	private Long id;

	public static MusicalId of( Long id ) {
		return new MusicalId( id );
	}
}
