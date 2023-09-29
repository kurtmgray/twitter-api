package com.cooksys.spring_assessment.services.impl;

import java.util.Optional;

import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.ValidateService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    
	  private final HashtagRepository hashtagRepository;
	  private final UserRepository userRepository;
	  
	  // checks whether hashtag exists
	  @Override
	  public boolean validateHashtag(String label) {
		  return hashtagRepository.findByLabel(label).isPresent();
	  }
		
	  // checks whether username exists (and is not deleted(?))
	  @Override
	  public boolean validateUsername(String username) {
		  Optional<User> user = userRepository.findByCredentialsUsername(username);
		  return user.isPresent();
	  }
}
