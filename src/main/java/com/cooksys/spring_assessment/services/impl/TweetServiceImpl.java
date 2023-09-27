package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotAuthorizedException;
import com.cooksys.spring_assessment.exceptions.NotFoundException;
import com.cooksys.spring_assessment.mappers.CredentialsMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.TweetService;

import com.cooksys.spring_assessment.services.UserService;
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
  	private final TweetMapper tweetMapper;
	private final UserService userService;

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<Tweet> tweets = tweetRepository.findByDeletedFalse(Sort.by(Sort.Order.desc("posted")));

		return tweetMapper.entitiesToDtos(tweets);
	}

	@Override
	public TweetResponseDto createSimpleTweet(TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new BadRequestException("Malformed request.");
		}
		User user = userService.validateUser(tweetRequestDto.getCredentials());
		Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto, user);

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public TweetResponseDto getTweetById(Long id) {
		Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (tweet.isEmpty()) {
			throw new NotFoundException("Tweet with id: " + id + "does not exist or is deleted.");
		}

		return tweetMapper.entityToDto(tweet.get());
	}

	@Override
	public TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto) {
		Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (tweet.isEmpty()) {
			throw new NotFoundException("Tweet with id: " + id + "does not exist or is deleted.");
		}

		User user = userService.validateUser(credentialsDto);
		if (!user.equals(tweet.get().getAuthor())) {
			throw new NotAuthorizedException("User is not author of tweet.");
		}

		Tweet tweetToSave = tweet.get();
		tweetToSave.setDeleted(true);
		tweetRepository.saveAndFlush(tweetToSave);

		return tweetMapper.entityToDto(tweet.get());
	}

}
