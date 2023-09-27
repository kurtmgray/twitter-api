package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.mappers.CredentialsMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.services.TweetService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
  	private final TweetMapper tweetMapper;
	private final CredentialsMapper credentialsMapper;

	@Override
	public TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new BadRequestException("Malformed request.");
		}

		Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

		Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);


		return null;
	}
}
