package com.foreach.across.samples.booking.application.domain.show;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowId
{
	private Long id;

	public static ShowId of( Long id ) {
		return new ShowId( id );
	}
}
