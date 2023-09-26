package com.cooksys.spring_assessment.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    } 

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }


    
}
