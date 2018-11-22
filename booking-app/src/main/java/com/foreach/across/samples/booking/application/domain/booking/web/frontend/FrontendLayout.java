package com.foreach.across.samples.booking.application.domain.booking.web.frontend;

import com.foreach.across.modules.web.template.LayoutTemplateProcessorAdapterBean;
import org.springframework.context.annotation.Configuration;

/**
 * Simple layout template for the frontend controllers.
 */
@Configuration
class FrontendLayout extends LayoutTemplateProcessorAdapterBean
{
	static final String TEMPLATE = "frontendLayout";

	public FrontendLayout() {
		super( TEMPLATE, "th/booking/frontend/layout" );
	}
}
