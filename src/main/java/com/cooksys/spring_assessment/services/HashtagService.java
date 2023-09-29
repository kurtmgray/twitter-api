package com.cooksys.spring_assessment.services;

import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {
	
	List<HashtagDto> getAllHashtags();
	
	List<TweetResponseDto> getAllTweetsWithLabel(String label);

}
