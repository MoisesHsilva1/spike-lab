package com.moisaas.the_moments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.moisaas.the_moments.configuration")
public class TheMomentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheMomentsApplication.class, args);
    }

}
