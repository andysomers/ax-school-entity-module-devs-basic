package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(using = ShowIdDeserializer.class)
public class ShowId
{
	private final String id;

	public static ShowId of( String id ) {
		return new ShowId( id );
	}
}
