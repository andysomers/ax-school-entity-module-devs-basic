package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import org.springframework.context.annotation.Configuration;

@Configuration
class SeatUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Seat.class )
		        .label( "seatNumber" );
	}
}
