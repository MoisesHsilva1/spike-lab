package com.moisaas.spike_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.moisaas.the_moments.configuration")
public class SpikeLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpikeLabApplication.class, args);
    }

}
