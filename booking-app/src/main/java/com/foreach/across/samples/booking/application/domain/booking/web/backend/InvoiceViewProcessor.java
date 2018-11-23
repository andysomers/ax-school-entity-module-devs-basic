package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.context.EntityViewContext;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SingleEntityFormViewProcessor;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.entity.web.links.EntityViewLinkBuilder;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.support.ContainerViewElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
class InvoiceViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void doPost( EntityViewRequest entityViewRequest, EntityView entityView, EntityViewCommand command, BindingResult bindingResult ) {
		if ( !bindingResult.hasErrors() ) {
			EntityViewContext entityViewContext = entityViewRequest.getEntityViewContext();
			EntityViewLinkBuilder linkBuilder = entityViewContext.getLinkBuilder();
			entityView.setRedirectUrl(
					linkBuilder.forInstance( entityViewContext.getEntity() ).updateView()
					           .withViewName( "invoice" )
					           .toUriString()
			);
		}
	}

	@Override
	protected void postRender( EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		ContainerViewElementUtils.move( container, "formGroup-followupList", SingleEntityFormViewProcessor.RIGHT_COLUMN );
	}
}
