package com.foreach.across.samples.booking.application.domain.show;

import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Show
{
	private ShowId id;
	private String location;
	private String city;
	private ZonedDateTime time;
	private MusicalId musicalId;

	public void setId( Long id ) {
		this.id = ShowId.of( id );
	}
}
