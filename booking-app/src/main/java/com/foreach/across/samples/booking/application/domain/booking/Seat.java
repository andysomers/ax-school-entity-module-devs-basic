package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.modules.hibernate.business.EntityWithDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Seat implements Persistable<Long>, EntityWithDto<Seat>
{
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	private Booking booking;

	@NotNull
	private TicketType ticketType;

	@NotBlank
	@Length(max = 5)
	private String seatNumber;

	@Override
	public Seat toDto() {
		return toBuilder().build();
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
}
