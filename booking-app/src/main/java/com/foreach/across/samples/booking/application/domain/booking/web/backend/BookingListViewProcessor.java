package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.bootstrapui.elements.GlyphIcon;
import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.bootstrapui.elements.TableViewElement;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.bootstrapui.processors.element.EntityListActionsProcessor;
import com.foreach.across.modules.entity.views.bootstrapui.util.SortableTableBuilder;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SortableTableRenderingViewProcessor;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.entity.views.util.EntityViewElementUtils;
import com.foreach.across.modules.entity.web.links.EntityViewLinkBuilder;
import com.foreach.across.modules.web.resource.WebResource;
import com.foreach.across.modules.web.resource.WebResourceRegistry;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.ViewElementPostProcessor;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilderSupport;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceStatus;
import org.springframework.stereotype.Component;

@Component
class BookingListViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void render( EntityViewRequest entityViewRequest,
	                       EntityView entityView,
	                       ContainerViewElementBuilderSupport<?, ?> containerBuilder,
	                       ViewElementBuilderMap builderMap,
	                       ViewElementBuilderContext builderContext ) {
		SortableTableBuilder tableBuilder = builderMap.get( SortableTableRenderingViewProcessor.TABLE_BUILDER, SortableTableBuilder.class );
		tableBuilder.valueRowProcessor( this::highlightRowBasedOnInvoiceStatus );
		tableBuilder.valueRowProcessor( addFollowupPopupButton( entityViewRequest.getEntityViewContext().getLinkBuilder() ) );
	}

	private void highlightRowBasedOnInvoiceStatus( ViewElementBuilderContext builderContext, TableViewElement.Row row ) {
		Booking booking = EntityViewElementUtils.currentEntity( builderContext, Booking.class );
		Invoice invoice = booking.getInvoice();

		if ( invoice == null ) {
			row.addCssClass( Style.DANGER.getName() );
		}
		else if ( invoice.getInvoiceStatus() == InvoiceStatus.PAID ) {
			row.addCssClass( Style.SUCCESS.getName() );
		}
		else if ( invoice.getInvoiceStatus() == InvoiceStatus.SENT ) {
			row.addCssClass( Style.WARNING.getName() );
		}
		else if ( invoice.getInvoiceStatus() == InvoiceStatus.CANCELLED ) {
			row.addCssClass( Style.INFO.getName() );
		}
	}

	private ViewElementPostProcessor<TableViewElement.Row> addFollowupPopupButton( EntityViewLinkBuilder linkBuilder ) {
		return ( builderContext, row ) ->
				row.find( EntityListActionsProcessor.CELL_NAME, TableViewElement.Cell.class )
				   .ifPresent( actions -> {
					   Booking booking = EntityViewElementUtils.currentEntity( builderContext, Booking.class );
					   String followupLink = linkBuilder.forInstance( booking ).withViewName( "addFollowup" ).withPartial( "::body" ).toUriString();

					   actions.addFirstChild(
							   BootstrapUiBuilders.button()
							                      .data( "popup", true )
							                      .link( followupLink )
							                      .iconOnly( new GlyphIcon( GlyphIcon.PAPERCLIP ) )
							                      .build( builderContext )
					   );
				   } );
	}

	@Override
	protected void registerWebResources( EntityViewRequest entityViewRequest, EntityView entityView, WebResourceRegistry webResourceRegistry ) {
		webResourceRegistry.add( WebResource.JAVASCRIPT_PAGE_END, "/static/booking/js/popup-view.js", WebResource.VIEWS );
	}
}
