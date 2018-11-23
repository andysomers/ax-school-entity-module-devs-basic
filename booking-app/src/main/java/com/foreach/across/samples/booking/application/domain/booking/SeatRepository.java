package com.foreach.across.samples.booking.application.domain.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Spring DATA JPA repository for a {@link Seat}.
 */
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat>
{
	List<Seat> findBySeatNumber( String seatNumber );
}
