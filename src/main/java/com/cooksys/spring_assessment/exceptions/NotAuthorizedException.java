package com.cooksys.spring_assessment.exceptions;

public class NotAuthorizedException extends SecurityException {

    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
