package com.metro.app.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6341835689857535565L;

    public ResourceNotFoundException( final String message ) {
        super( message );
    }
}
