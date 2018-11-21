package com.foreach.across.samples.entity;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.web.AcrossWebModule;
import com.foreach.across.modules.debugweb.DebugWebModule;
import com.foreach.across.modules.logging.LoggingModule;
import com.foreach.across.modules.applicationinfo.ApplicationInfoModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@AcrossApplication(
		modules = {
				AcrossWebModule.NAME,
				DebugWebModule.NAME,
				LoggingModule.NAME,
				ApplicationInfoModule.NAME
		}
)
public class BookingApplication
{
	public static void main( String[] args ) {
		SpringApplication springApplication = new SpringApplication( BookingApplication.class );
		springApplication.setDefaultProperties( Collections.singletonMap( "spring.config.location", "${user.home}/dev-configs/booking-application.yml" ) );
		springApplication.run( args );
	}
}