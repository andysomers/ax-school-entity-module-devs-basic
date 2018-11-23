package com.foreach.across.samples.booking.application.domain.musical;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical implements Persistable<MusicalId>
{
	@Id
	private MusicalId id;

	private String name;
	private String description;

	@Override
	public boolean isNew() {
		return id == null;
	}
}
