package com.metro.app;

import com.metro.app.config.MetroConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({MetroConfig.class})
@SpringBootApplication
public class MetroApp {
    public static void main(final String[] args) {
        SpringApplication.run(MetroApp.class, args);
    }
}
