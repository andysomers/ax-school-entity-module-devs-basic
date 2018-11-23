package com.foreach.across.samples.modules.invoice.config;

import com.foreach.across.modules.hibernate.jpa.repositories.config.EnableAcrossJpaRepositories;
import com.foreach.across.samples.modules.invoice.InvoiceModule;
import com.foreach.across.samples.modules.invoice.domain.OmegaDomain;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = InvoiceModule.class)
@EnableAcrossJpaRepositories(basePackageClasses = OmegaDomain.class)
public class DomainConfiguration {

}
