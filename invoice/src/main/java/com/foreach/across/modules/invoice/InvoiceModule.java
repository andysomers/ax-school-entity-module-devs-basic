package com.foreach.across.modules.invoice;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ApplicationContextConfigurer;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;

import java.util.Set;

public class InvoiceModule extends AcrossModule {
    @Override
    public String getName() {
        return "InvoiceModule";
    }

    @Override
    protected void registerDefaultApplicationContextConfigurers( Set<ApplicationContextConfigurer> contextConfigurers ) {
        contextConfigurers.add( ComponentScanConfigurer.forAcrossModule( InvoiceModule.class ) );
    }
}
