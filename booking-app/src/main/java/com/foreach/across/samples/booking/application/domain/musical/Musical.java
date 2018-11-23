package com.foreach.across.samples.booking.application.domain.musical;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical
{
	@Id
	@Setter(AccessLevel.PRIVATE)
	private MusicalId id;

	@NotBlank
	@Length(max = 255)
	private String name;

	private String description;
}
