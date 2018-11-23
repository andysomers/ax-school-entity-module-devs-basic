package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.entity.views.bootstrapui.EmbeddedCollectionOrMapElementBuilderFactory;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
class SeatsForBookingUiConfiguration implements EntityConfigurer
{
	private final SeatRepository seatRepository;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .properties(
				        props -> props.property( "seats" )
				                      .propertyType( TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( Seat.class ) ) )
				                      .valueFetcher( seatRepository::findByBookingOrderBySeatNumberAsc )
				                      .viewElementType( ViewElementMode.VALUE, EmbeddedCollectionOrMapElementBuilderFactory.ELEMENT_TYPE )
		        )
		        .association(
				        association -> association.name( "seat.booking" )
				                                  .associationType( EntityAssociation.Type.EMBEDDED )
		        );
	}
}
