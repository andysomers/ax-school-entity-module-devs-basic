package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.NumericFormElementConfiguration;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
class InvoiceUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Invoice.class )
		        .properties(
				        props -> props.property( "amount" )
				                      .attribute( NumericFormElementConfiguration.class,
				                                  NumericFormElementConfiguration.currency( Currency.getInstance( "EUR" ), 2, true ) )
		        );
	}
}
