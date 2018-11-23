package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foreach.across.samples.booking.application.domain.show.support.ShowIdDeserializer;
import com.foreach.across.samples.booking.application.domain.show.support.ShowIdSerializer;
import lombok.Data;

@Data
@JsonDeserialize(using = ShowIdDeserializer.class)
@JsonSerialize(using = ShowIdSerializer.class)
public class ShowId
{
	private final String id;

	public static ShowId of( String id ) {
		return new ShowId( id );
	}
}
