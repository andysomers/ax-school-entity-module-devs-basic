package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical
{
	@Setter(AccessLevel.PRIVATE)
	private MusicalId id;

	private String name;

	private String description;
}
