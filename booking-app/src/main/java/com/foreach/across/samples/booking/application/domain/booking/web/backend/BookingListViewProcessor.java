package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.bootstrapui.util.SortableTableBuilder;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SortableTableRenderingViewProcessor;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.entity.views.util.EntityViewElementUtils;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
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
		tableBuilder.valueRowProcessor(
				( rowBuilderContext, row ) -> {
					Booking booking = EntityViewElementUtils.currentEntity( rowBuilderContext, Booking.class );
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
		);
	}
}
