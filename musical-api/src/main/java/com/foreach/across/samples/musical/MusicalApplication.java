package com.foreach.across.samples.musical;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.web.AcrossWebModule;
import org.springframework.boot.SpringApplication;

import java.util.Collections;

@AcrossApplication(
        modules = {
                AcrossWebModule.NAME,
                AcrossHibernateJpaModule.NAME,
                EntityModule.NAME
        }
)
public class MusicalApplication {
        public static void main( String[] args ) {
                SpringApplication springApplication = new SpringApplication( MusicalApplication.class );
                springApplication.run( args );
        }
}
