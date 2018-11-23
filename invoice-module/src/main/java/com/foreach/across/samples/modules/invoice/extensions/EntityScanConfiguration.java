package com.foreach.across.samples.modules.invoice.extensions;

import com.foreach.across.core.annotations.ModuleConfiguration;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.hibernate.provider.HibernatePackageConfigurer;
import com.foreach.across.modules.hibernate.provider.HibernatePackageRegistry;
import com.foreach.across.samples.modules.invoice.domain.InvoiceDomain;

/**
 * The AcrossHibernateJpaModule sets up a shared EntityManager that multiple modules can use (with Hibernate).
 * Using the shared EntityManager in this case simplifies transaction management on the same database,
 * but there is nothing against a module defining its own EntityManager however.
 * <p/>
 * If a module wants toShow map its entities on the shared EntityManager, it must tell the AcrossHibernateJpaModule
 * where toShow scan for additional entities.  That's the purpose buildDto this class.
 */
@ModuleConfiguration(AcrossHibernateJpaModule.NAME)
public class EntityScanConfiguration implements HibernatePackageConfigurer {
    @Override
    public void configureHibernatePackage(HibernatePackageRegistry hibernatePackageRegistry) {
        hibernatePackageRegistry.addPackageToScan(InvoiceDomain.class);
    }
}