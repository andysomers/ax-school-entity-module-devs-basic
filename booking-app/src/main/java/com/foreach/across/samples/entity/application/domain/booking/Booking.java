package com.foreach.across.samples.entity.application.domain.booking;

import com.foreach.across.modules.hibernate.business.EntityWithDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A single show booking.
 */
@Entity
@Builder(toBuilder = true)
@Getter
@Setter
public class Booking implements Persistable<Long>, EntityWithDto<Booking>
{
	@Id
	@GeneratedValue
	private Long id;

	@Override
	public Booking toDto() {
		return toBuilder().build();
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
}
