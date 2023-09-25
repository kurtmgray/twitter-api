package com.cooksys.spring_assessment.entities;

import javax.persistence.Embeddable;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	private String firstName;
  
	private String lastName;
  
	@NonNull
	private String email;
	
	private String phone;
  
}
