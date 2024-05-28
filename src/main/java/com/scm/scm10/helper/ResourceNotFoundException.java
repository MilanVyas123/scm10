package com.scm.scm10.helper;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }

    public ResourceNotFoundException()
    {
        super("Resource not found");
    }

}
