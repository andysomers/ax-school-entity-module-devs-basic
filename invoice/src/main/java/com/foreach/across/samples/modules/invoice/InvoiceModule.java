package com.foreach.across.samples.modules.invoice;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.core.context.configurer.ApplicationContextConfigurer;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;

import java.util.Set;

@AcrossDepends(required = {
        EntityModule.NAME,
        AcrossHibernateJpaModule.NAME
})
public class InvoiceModule extends AcrossModule {
    public static final String NAME = "WebCmsModule";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void registerDefaultApplicationContextConfigurers(Set<ApplicationContextConfigurer> contextConfigurers) {
        contextConfigurers.add(ComponentScanConfigurer.forAcrossModule(InvoiceModule.class));
    }
}
