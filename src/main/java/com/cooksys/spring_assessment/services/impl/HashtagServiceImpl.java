package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.services.HashtagService;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.mappers.HashtagMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.Hashtag;
import com.cooksys.spring_assessment.exceptions.BadRequestException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

  private final HashtagRepository hashtagRepository;
  private final HashtagMapper hashtagMapper;
  
  private final TweetMapper tweetMapper;
  
  private final Comparator<Tweet> postedDateComparator = (t1, t2) -> t2.getPosted().compareTo(t1.getPosted());

  @Override
  public List<HashtagDto> getAllHashtags() {
	  return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
  }
  
  @Override
	public List<TweetResponseDto> getAllTweetsWithLabel(String label) {
	  Optional<Hashtag> tag = hashtagRepository.findByLabel(label);
	  if (tag.isEmpty()) {
		  throw new BadRequestException("No hashtag with that label exists.");
	  }
	  
		List<Tweet> labeledTweets = tag.get().getTweets();
		labeledTweets.sort(postedDateComparator);
		return tweetMapper.entitiesToDtos(labeledTweets);
	}
}