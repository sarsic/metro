package com.metro.app.config;

import com.metro.app.error.RestExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestResourcesConfig {

    @Bean
    public RestExceptionHandler exceptionHandler() {
        return new RestExceptionHandler();
    }
}
