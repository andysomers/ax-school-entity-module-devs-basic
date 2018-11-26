package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foreach.across.samples.booking.application.domain.show.support.ShowIdDeserializer;
import com.foreach.across.samples.booking.application.domain.show.support.ShowIdSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonDeserialize(using = ShowIdDeserializer.class)
@JsonSerialize(using = ShowIdSerializer.class)
public class ShowId implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final String id;

	@Override
	public String toString() {
		return id;
	}

	public static ShowId of( String id ) {
		return id != null ? new ShowId( id ) : null;
	}
}
