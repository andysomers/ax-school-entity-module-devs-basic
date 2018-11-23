package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.support.ContainerViewElementUtils;
import org.springframework.stereotype.Component;

@Component
class BookingSummaryViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void postRender( EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		container.addChild(
				BootstrapUiBuilders.row()
				                   .add( BootstrapUiBuilders.div().name( "col-1" ).css( "col-md-4" ) )
				                   .add( BootstrapUiBuilders.div().name( "col-2" ).css( "col-md-4" ) )
				                   .add( BootstrapUiBuilders.div().name( "col-3" ).css( "col-md-4" ) )
				                   .build( builderContext )
		);

		ContainerViewElementUtils.move( container, "invoice", "col-1" );
		ContainerViewElementUtils.move( container, "formGroup-followupList", "col-2" );
		ContainerViewElementUtils.move( container, "formGroup-seats", "col-3" );
	}
}
