package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;

@Configuration
class BookingUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .properties(
				        props -> props.property( "created" )
				                      .writable( false )
				                      .attribute( EntityAttributes.PROPERTY_REQUIRED, true )
				                      .and()
				                      .property( "email" )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase() )
				                      .and()
				                      .property( "name" )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase() )
				                      .and()
				                      .property( "searchText" )
				                      .propertyType( String.class )
				                      .hidden( true )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.expandingOr( "email", "name" ) )

		        )
		        .attribute( EntityAttributes.LINK_TO_DETAIL_VIEW, true )
		        .listView(
				        lvb -> lvb.showProperties( ".", "~ticketType" )
				                  .defaultSort( new Sort( Sort.Direction.DESC, "created" ) )
				                  .entityQueryFilter( filter -> filter.showProperties( "ticketType", "searchText" ) )
		        )
		        .createFormView(
				        fvb -> fvb.properties( props -> props.property( "created" ).writable( true ) )
		        )
		        .updateFormView(
				        fvb -> fvb.showProperties( ".", "created" )
		        )
		;
	}

	@EventListener
	public void createRootNavigationSection( AdminMenuEvent menu ) {
		menu.group( "/entities/BookingApplicationModule" ).changePathTo( "/studio-across" );
	}
}
