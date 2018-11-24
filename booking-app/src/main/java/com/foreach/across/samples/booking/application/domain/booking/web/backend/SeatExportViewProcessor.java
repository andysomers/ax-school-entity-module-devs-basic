package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.bootstrapui.elements.GlyphIcon;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.AbstractEntityFetchingViewProcessor;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilderSupport;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class SeatExportViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void doGet( EntityViewRequest entityViewRequest, EntityView entityView, EntityViewCommand command ) {
		if ( entityViewRequest.getWebRequest().getParameter( "export" ) != null ) {
			Iterable<Seat> items = entityView.getAttribute( AbstractEntityFetchingViewProcessor.DEFAULT_ATTRIBUTE_NAME, Iterable.class );

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.valueOf( "text/csv" ) );
			headers.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.csv" );

			StringBuilder export = new StringBuilder();
			items.forEach( seat -> export.append( seat.getSeatNumber() ).append( "," ).append( seat.getBooking().getName() ).append( "\n" ) );
			entityView.setResponseEntity( new ResponseEntity<>( export, headers, HttpStatus.OK ) );
			entityView.setShouldRender( false );
		}
	}

	@Override
	protected void render( EntityViewRequest entityViewRequest,
	                       EntityView entityView,
	                       ContainerViewElementBuilderSupport<?, ?> containerBuilder,
	                       ViewElementBuilderMap builderMap,
	                       ViewElementBuilderContext builderContext ) {
		ContainerViewElementBuilderSupport buttons = builderMap.get( "entityListForm-actions", ContainerViewElementBuilderSupport.class );

		buttons.add(
				BootstrapUiBuilders.button()
				                   .submit()
				                   .name( "export" )
				                   .text( " Export" )
				                   .attribute( "value", "export" )
				                   .icon( new GlyphIcon( GlyphIcon.DOWNLOAD_ALT ) )
		);
	}

	@Override
	protected void postRender( EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		container.removeFromTree( "btn-create" );
	}
}
