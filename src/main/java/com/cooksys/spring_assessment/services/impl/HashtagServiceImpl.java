package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.services.HashtagService;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.mappers.HashtagMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Tweet;

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
  
  private final TweetRepository tweetRepository;
  private final TweetMapper tweetMapper;
  
  private final Comparator<Tweet> postedDateComparator = (t1, t2) -> t2.getPosted().compareTo(t1.getPosted());

  
  @Override
  public List<HashtagDto> getAllHashtags() {
	  return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
  }
  
  @Override
	public List<TweetResponseDto> getAllTweetsWithLabel(String label) {
		Optional<List<Tweet>> getTweets = tweetRepository.findByTweetHashtagsAndDeletedFalse(label);
		if (!getTweets.isEmpty()) {
			List<Tweet> labeledTweets = getTweets.get();
			labeledTweets.sort(postedDateComparator);
			return tweetMapper.entitiesToDtos(labeledTweets);
		}
		return new ArrayList<TweetResponseDto>();
	}
}