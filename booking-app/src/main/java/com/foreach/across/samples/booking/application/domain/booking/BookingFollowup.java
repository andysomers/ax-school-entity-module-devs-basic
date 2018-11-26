package com.foreach.across.samples.booking.application.domain.booking;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Embeddable
@Data
public class BookingFollowup
{
	@NotNull
	private ZonedDateTime time;

	private String remarks;
}
