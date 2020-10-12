package com.metro.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metro.app.jpa.repository.OrderRepository;
import com.metro.app.jpa.repository.ProductRepository;
import com.metro.app.service.OrderService;
import com.metro.app.service.ProductService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestResourcesConfig.class)
public class MetroConfig {
    @Bean
    public OpenAPI springMetroOpenAPI() {
        return new OpenAPI().info(new Info().title("Metro API")
                                            .description(
                                                    "Application for Products manipulation, placing orders and listing orders")
                                            .version("v0.0.1"));
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public ProductService productService(final ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    public OrderService orderService(final ProductRepository productRepository, final OrderRepository orderRepository) {
        return new OrderService(orderRepository, productRepository);
    }
}
