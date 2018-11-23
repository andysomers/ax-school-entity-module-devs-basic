package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Show
{
	@Id
	@Setter(AccessLevel.PRIVATE)
	private ShowId id;

	@NotNull
	private MusicalId musicalId;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private ZonedDateTime time;

	@NotBlank
	private String location;

	@NotBlank
	private String city;
}
