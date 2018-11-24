package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookingsForInvoiceAssociation implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Invoice.class )
		        .association( association -> association.name( "booking.invoice" ).hide() );
	}
}
