package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.mappers.CredentialsMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.TweetService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
  	private final TweetMapper tweetMapper;
	private final CredentialsMapper credentialsMapper;

	// TODO: map username and email
	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<Tweet> tweets = tweetRepository.findByDeletedFalse(Sort.by(Sort.Order.desc("posted")));

		return tweetMapper.entitiesToDtos(tweets);
	}
	@Override
	public TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto) {
		// validate dto
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new BadRequestException("Malformed request.");
		}

		// validate user
		Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(), credentials.getPassword());
		if (optionalUser.isEmpty()) {
			throw new BadRequestException("Invalid credentials.");
		}
//		if (optionalUser.isEmpty()) {
//			throw new BadRequestException("Invalid username.");
//		}
//		optionalUser = userRepository.findByCredentialsPassword(credentials.getPassword());
//		if (optionalUser.isEmpty()) {
//			throw new BadRequestException("Invalid password.");
//		}

		User user = optionalUser.get();
		Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
		tweet.setAuthor(user);


		return tweetMapper.entityToDto(tweet);
	}


}
