package com.foreach.across.samples.booking;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.modules.web.AcrossWebModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@AcrossApplication(
        modules = {
                AcrossWebModule.NAME,
        }
)
public class BookingApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BookingApplication.class);
        springApplication.run(args);
    }
}