package com.foreach.across.samples.booking.application.domain.show;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowId
{
	private String id;

	public static ShowId of( String id ) {
		return new ShowId( id );
	}
}
