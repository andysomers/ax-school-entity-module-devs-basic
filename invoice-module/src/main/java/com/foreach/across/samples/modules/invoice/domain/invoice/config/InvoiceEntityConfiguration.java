package com.foreach.across.samples.modules.invoice.domain.invoice.config;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
public class InvoiceEntityConfiguration implements EntityConfigurer {
    @Override
    public void configure(EntitiesConfigurationBuilder entities) {
        entities.withType(Invoice.class)
                .properties(props -> props
                        .property("#label").spelValueFetcher("'invoice-' + id").and()
                        .property("amount").attribute(Currency.class, Currency.getInstance("EUR"))
                );
    }
}
