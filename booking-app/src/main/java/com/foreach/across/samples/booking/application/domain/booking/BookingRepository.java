package com.foreach.across.samples.booking.application.domain.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring DATA JPA repository for the {@link Booking}.
 */
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking>
{
}
