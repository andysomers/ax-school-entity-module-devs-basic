package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.builder.ColumnViewElementBuilder;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.context.EntityViewContext;
import com.foreach.across.modules.entity.views.processors.ExtensionViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SingleEntityFormViewProcessor;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilderSupport;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.BookingFollowup;
import com.foreach.across.samples.booking.application.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;

import java.time.ZonedDateTime;

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.*;

@Component
@RequiredArgsConstructor
class AddFollowupViewProcessor extends ExtensionViewProcessorAdapter<BookingFollowup>
{
	private final BookingRepository bookingRepository;

	@Override
	protected BookingFollowup createExtension( EntityViewRequest entityViewRequest, EntityViewCommand command, WebDataBinder dataBinder ) {
		BookingFollowup followup = new BookingFollowup();
		followup.setTime( ZonedDateTime.now() );
		return followup;
	}

	@Override
	protected void doPost( BookingFollowup followup, BindingResult bindingResult, EntityView entityView, EntityViewRequest entityViewRequest ) {
		if ( !bindingResult.hasErrors() ) {
			EntityViewContext entityViewContext = entityViewRequest.getEntityViewContext();

			Booking booking = entityViewContext.getEntity( Booking.class ).toDto();
			booking.getFollowupList().add( followup );
			bookingRepository.save( booking );

			entityView.setRedirectUrl(
					entityViewContext.getLinkBuilder()
					                 .forInstance( entityViewContext.getEntity() )
					                 .updateView()
					                 .withViewName( "addFollowup" )
					                 .withPartial( entityViewRequest.getPartialFragment() )
					                 .withQueryParam( "success", true )
					                 .toUriString()
			);
		}
	}

	@Override
	protected void validateExtension( BookingFollowup extension, Errors errors, HttpMethod httpMethod, EntityViewRequest entityViewRequest ) {
		if ( HttpMethod.POST == httpMethod && StringUtils.isBlank( extension.getRemarks() ) ) {
			errors.rejectValue( "remarks", "NotBlank" );
		}
	}

	@Override
	protected void render( BookingFollowup extension,
	                       EntityViewRequest entityViewRequest,
	                       EntityView entityView,
	                       ContainerViewElementBuilderSupport<?, ?> containerBuilder,
	                       ViewElementBuilderMap builderMap,
	                       ViewElementBuilderContext builderContext ) {
		builderMap.get( SingleEntityFormViewProcessor.LEFT_COLUMN, ColumnViewElementBuilder.class )
		          .add(
				          formGroup()
						          .label( "Time" )
						          .required()
						          .control(
								          datetime().controlName( controlPrefix() + ".time" )
								                    .value( extension.getTime() != null ? extension.getTime().toLocalDateTime() : null )
								                    .postProcessor( ( bc, d ) -> d.getChildren() )  // workaround for AXBUM-27
						          )
		          )
		          .add(
				          formGroup()
						          .label( "Remarks" )
						          .control(
								          textarea().controlName( controlPrefix() + ".remarks" )
								                    .text( extension.getRemarks() )
						          )
		          );
	}

	@Override
	protected void postRender( BookingFollowup extension,
	                           EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		if ( entityViewRequest.getWebRequest().getParameter( "success" ) != null ) {
			container.clearChildren();
			container.addChild(
					alert().success()
					       .text( entityViewRequest.getEntityViewContext().getMessageCodeResolver().getMessage( "followupAdded", "ok" ) )
					       .build( builderContext )
			);
		}
	}
}
