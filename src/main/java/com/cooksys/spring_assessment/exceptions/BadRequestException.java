package com.cooksys.spring_assessment.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    private String message;

}
