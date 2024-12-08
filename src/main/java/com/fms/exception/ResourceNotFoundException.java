package com.fms.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String reference) {
        super("Unable to find resource {" + resourceName + "} with reference {" + reference + "}");
    }

    public ResourceNotFoundException(String reason) {
        super(reason);
    }
}