package com.foreach.across.samples.musical;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.web.AcrossWebModule;
import org.springframework.boot.SpringApplication;

@AcrossApplication(
        modules = {
                AcrossWebModule.NAME,
                AcrossHibernateJpaModule.NAME,
                EntityModule.NAME
        }
)
public class MusicalApiApplication {
        public static void main( String[] args ) {
                SpringApplication springApplication = new SpringApplication(MusicalApiApplication.class);
                springApplication.run( args );
        }
}
