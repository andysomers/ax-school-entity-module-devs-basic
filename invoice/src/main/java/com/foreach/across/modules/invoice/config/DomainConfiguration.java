package com.foreach.across.modules.invoice.config;

import com.foreach.across.modules.hibernate.jpa.repositories.config.EnableAcrossJpaRepositories;
import com.foreach.across.modules.invoice.InvoiceModule;
import com.foreach.across.modules.invoice.domain.AbstractDomain;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = InvoiceModule.class)
@EnableAcrossJpaRepositories(basePackageClasses = AbstractDomain.class)
public class DomainConfiguration {

}
