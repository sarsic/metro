package com.metro.app.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleMissingOrders(final ResourceNotFoundException ex) {
        LOG.info("Resource not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested resource cannot be found, " + ex.getMessage());
    }
}
