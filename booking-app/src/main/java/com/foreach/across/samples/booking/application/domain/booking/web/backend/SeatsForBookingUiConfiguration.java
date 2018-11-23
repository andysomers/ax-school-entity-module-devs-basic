package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import org.springframework.context.annotation.Configuration;

@Configuration
class SeatsForBookingUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .association(
				        association -> association.name( "seat.booking" )
				                                  .associationType( EntityAssociation.Type.EMBEDDED )
		        );
	}
}
