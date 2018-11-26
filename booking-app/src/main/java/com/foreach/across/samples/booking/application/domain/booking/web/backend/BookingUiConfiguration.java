package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.bootstrapui.components.builder.NavComponentBuilder;
import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.bootstrapui.elements.GlyphIcon;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.actions.EntityConfigurationAllowableActionsBuilder;
import com.foreach.across.modules.entity.actions.FixedEntityAllowableActionsBuilder;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.config.builders.EntityConfigurationBuilder;
import com.foreach.across.modules.entity.config.builders.EntityPropertyRegistryBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.modules.entity.registry.properties.*;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.EntityViewCustomizers;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.entity.views.bootstrapui.elements.ViewElementFieldset;
import com.foreach.across.modules.entity.views.util.EntityViewElementUtils;
import com.foreach.across.modules.entity.web.links.EntityViewLinks;
import com.foreach.across.modules.spring.security.actions.AllowableAction;
import com.foreach.across.modules.spring.security.actions.AuthorityMatchingAllowableActions;
import com.foreach.across.modules.spring.security.authority.AuthorityMatcher;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.show.Show;
import com.foreach.across.samples.booking.application.domain.show.ShowClient;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
class BookingUiConfiguration implements EntityConfigurer
{
	private final ShowClient showClient;
	private final InvoiceRepository invoiceRepository;
	private final Validator entityValidator;

	private final BookingListViewProcessor bookingListViewProcessor;
	private final InvoiceViewProcessor invoiceViewProcessor;
	private final BookingSummaryViewProcessor bookingSummaryViewProcessor;
	private final AddFollowupViewProcessor addFollowupViewProcessor;

	private final EntityViewLinks entityViewLinks;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .allowableActionsBuilder( bookingAllowableActions() )
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
				                      .and()
				                      .property( "invoice" )
				                      .hidden( true )
				                      .attribute( EntityPropertyHandlingType.class, EntityPropertyHandlingType.BINDER )
				                      .attribute( EntityAttributes.IS_EMBEDDED_OBJECT, true )
				                      .controller( this::invoiceController )
				                      .and()
				                      .property( "followupList" ).hidden( true )

		        )
		        .properties( this::configureShowProperties )
		        .attribute( EntityAttributes.LINK_TO_DETAIL_VIEW, true )
		        .listView(
				        lvb -> lvb.showProperties( ".", "~ticketType" )
				                  .defaultSort( new Sort( Sort.Direction.DESC, "created" ) )
				                  .entityQueryFilter( filter -> filter.showProperties( "ticketType", "searchText" ) )
				                  .viewProcessor( bookingListViewProcessor )
		        )
		        .createFormView(
				        fvb -> fvb.properties( props -> props.property( "created" ).writable( true ) )
		        )
		        .updateFormView(
				        fvb -> fvb.showProperties( ".", "created" )
		        )
		        .formView(
				        "invoice",
				        EntityViewCustomizers.basicSettings()
				                             .adminMenu( "/invoice" )
				                             .andThen( fvb -> fvb.showProperties( "invoice.*", "followupList" ) )
				                             .andThen( fvb -> fvb.viewProcessor( invoiceViewProcessor ) )
		        )
		        .view(
				        EntityView.SUMMARY_VIEW_NAME,
				        vb -> vb.showProperties( "invoice", "seats", "followupList" )
				                .properties(
						                props -> props.property( "invoice" )
						                              .attribute( EntityAttributes.FIELDSET_PROPERTY_SELECTOR,
						                                          EntityPropertySelector
								                                          .of( "invoice.amount", "invoice.invoiceDate", "invoice.invoiceStatus" ) )
						                              .attribute( ViewElementFieldset.TEMPLATE, ViewElementFieldset.TEMPLATE_PANEL_DEFAULT )
				                )
				                .viewProcessor( bookingSummaryViewProcessor )
		        )
		        .and( this::configureAddFollowupView )
		        .viewElementBuilder( ViewElementMode.LIST_VALUE, builderContext -> {
			        Booking booking = EntityViewElementUtils.currentPropertyValue( builderContext, Booking.class );
			        return BootstrapUiBuilders.link()
			                                  .text( booking.getName() )
			                                  .url( entityViewLinks.linkTo( booking ).toUriString() )
			                                  .build( builderContext );
		        } );
	}

	private EntityConfigurationAllowableActionsBuilder bookingAllowableActions() {
		Map<AllowableAction, AuthorityMatcher> mappings = new HashMap<>();
		mappings.put( AllowableAction.READ, AuthorityMatcher.allOf( "booking-department" ) );
		mappings.put( AllowableAction.CREATE, AuthorityMatcher.allOf( "booking-department" ) );
		mappings.put( AllowableAction.UPDATE, AuthorityMatcher.allOf( "booking-department" ) );
		mappings.put( AllowableAction.DELETE, AuthorityMatcher.allOf( "booking-department" ) );
		mappings.put( AllowableAction.ADMINISTER, AuthorityMatcher.allOf( "booking-department" ) );

		return new FixedEntityAllowableActionsBuilder( AuthorityMatchingAllowableActions.forSecurityContext( mappings ) );
	}

	private void configureAddFollowupView( EntityConfigurationBuilder<Booking> builder ) {
		builder.formView(
				"addFollowup",
				EntityViewCustomizers.basicSettings()
				                     .adminMenu(
						                     "/advanced-options/addFollowup",
						                     e -> e.attribute( NavComponentBuilder.ATTR_ICON, new GlyphIcon( GlyphIcon.PAPERCLIP ) )
						                           .attribute( NavComponentBuilder.ATTR_ICON_ONLY, true )
				                     )
				                     .andThen( EntityViewCustomizers.formSettings().forExtension( true ) )
				                     .andThen( vb -> vb.viewProcessor( addFollowupViewProcessor ) )
		);
	}

	private void invoiceController( ConfigurableEntityPropertyController<?, ?> controller ) {
		controller.withTarget( Booking.class, Invoice.class )
		          .order( EntityPropertyController.BEFORE_ENTITY )
		          .createValueFunction( booking -> Invoice.builder()
		                                                  .amount( booking.getNumberOfTickets() * 25D )
		                                                  .name( booking.getName() )
		                                                  .email( booking.getEmail() )
		                                                  .invoiceDate( LocalDate.now() )
		                                                  .build() )
		          .valueFetcher( booking -> booking.getInvoice() != null ? booking.getInvoice().toDto() : null )
		          .validator( EntityPropertyValidator.of( entityValidator ) )
		          .saveConsumer( ( booking, invoiceValue ) -> {
			          booking.setInvoice( invoiceRepository.save( invoiceValue.getNewValue() ) );
		          } )
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
