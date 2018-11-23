package com.foreach.across.samples.modules.invoice.extensions;

import com.foreach.across.core.annotations.ModuleConfiguration;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.samples.modules.invoice.InvoiceModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ModuleConfiguration(AcrossHibernateJpaModule.NAME)
@EntityScan(basePackageClasses = InvoiceModule.class)
public class EntityScanConfiguration
{
}