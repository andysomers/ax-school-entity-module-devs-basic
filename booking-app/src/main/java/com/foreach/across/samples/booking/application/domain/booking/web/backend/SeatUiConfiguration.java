package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.views.processors.SortableTableRenderingViewProcessor;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
class SeatUiConfiguration implements EntityConfigurer
{
	private final SeatExportViewProcessor exportViewProcessor;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Seat.class )
		        .label( "seatNumber" )
		        .listView(
				        lvb -> lvb.entityQueryFilter( eqf -> eqf.showProperties( "ticketType" ) )
				                  .viewProcessor( exportViewProcessor, Ordered.LOWEST_PRECEDENCE )
				                  .postProcess( SortableTableRenderingViewProcessor.class, table -> table.setIncludeDefaultActions( false ) )
				                  .pageSize( 1000 )
		        );
	}
}
