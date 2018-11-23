package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@Setter(AccessLevel.PRIVATE)
	private ShowId id;

	private String location;

	private String city;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private ZonedDateTime time;

	private MusicalId musicalId;
}
