package com.cooksys.spring_assessment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.spring_assessment.services.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
	  
	private final ValidateService validateService;
	
    @GetMapping("/tag/exists/{label}")
	public ResponseEntity<Boolean> validateHashtag(@PathVariable String label) {
		return new ResponseEntity<>(validateService.validateHashtag(label), HttpStatus.OK);
	}

    @GetMapping("/username/exists/@{username}")
    public ResponseEntity<Boolean> validateUsername(@PathVariable String username) {
	    return new ResponseEntity<>(validateService.validateUsername(username), HttpStatus.OK);
	}

    @GetMapping("/username/available/@{username}")
    public ResponseEntity<Boolean> validateUsernameAvailability(@PathVariable String username) {
    	return new ResponseEntity<>(!validateService.validateUsername(username), HttpStatus.OK);
    }

}
