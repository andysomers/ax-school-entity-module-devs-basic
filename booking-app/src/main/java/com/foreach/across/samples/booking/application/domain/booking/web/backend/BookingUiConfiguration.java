package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.config.builders.EntityPropertyRegistryBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.show.Show;
import com.foreach.across.samples.booking.application.domain.show.ShowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;

@Configuration
@RequiredArgsConstructor
class BookingUiConfiguration implements EntityConfigurer
{
	private final ShowClient showClient;

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
		        .properties( this::configureShowProperties )
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

	private void configureShowProperties( EntityPropertyRegistryBuilder properties ) {
		properties.property( "showId" ).hidden( true ).and()
		          .property( "show" )
		          .propertyType( Show.class )
		          .order( Ordered.HIGHEST_PRECEDENCE )
		          .controller(
				          controller -> controller.withTarget( Booking.class, Show.class )
				                                  .valueFetcher( booking -> booking.getShowId() != null ? showClient.getShow( booking.getShowId() ) : null )
				                                  .applyValueConsumer(
						                                  ( booking, show ) -> booking
								                                  .setShowId( show.getNewValue() != null ? show.getNewValue().getId() : null )
				                                  )
				                                  .contextualValidator( ( booking, show, errors, validationHints ) -> {
					                                  if ( show == null ) {
						                                  errors.rejectValue( "", "NotNull" );
					                                  }
				                                  } )
		          )
		          .writable( true )
		          .attribute( EntityAttributes.PROPERTY_REQUIRED, true );
	}

	@EventListener
	public void createRootNavigationSection( AdminMenuEvent menu ) {
		menu.group( "/entities/BookingApplicationModule" ).changePathTo( "/studio-across" );
	}
}
