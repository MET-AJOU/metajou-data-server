package com.minshigee.dataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DataServerApplication {

    private static final String PROPERTIES = "spring.config.location=" +
            "classpath:/application.properties" +
            ",classpath:/secret.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(DataServerApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }

}
