package com.foreach.across.samples.entity;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.web.AcrossWebModule;
import org.springframework.boot.SpringApplication;

import java.util.Collections;

@AcrossApplication(
		modules = {
				AcrossWebModule.NAME
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