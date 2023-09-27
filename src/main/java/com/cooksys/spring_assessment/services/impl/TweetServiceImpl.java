package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.dtos.TweetRequestDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Hashtag;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotAuthorizedException;
import com.cooksys.spring_assessment.exceptions.NotFoundException;
import com.cooksys.spring_assessment.mappers.HashtagMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.TweetService;

import com.cooksys.spring_assessment.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
  	private final TweetMapper tweetMapper;
	private final UserService userService;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;


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
	public TweetResponseDto getTweet(Long id) {
		Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (tweet.isEmpty()) {
			throw new NotFoundException("Tweet with id " + id + " does not exist or is deleted.");
		}

		return tweetMapper.entityToDto(tweet.get());
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		Tweet tweet = validateTweet(id);
		User user = userService.validateUser(credentialsDto);

		if (!user.equals(tweet.getAuthor())) {
			throw new NotAuthorizedException("User is not author of tweet.");
		}

		TweetResponseDto response = tweetMapper.entityToDto(tweet);

		tweet.setDeleted(true);
		tweetRepository.saveAndFlush(tweet);

		return response;
	}

	@Override
	public TweetResponseDto addTweetReply(Long id, TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new BadRequestException("Malformed request.");
		}

		Tweet tweet = validateTweet(id);

		User user = userService.validateUser(tweetRequestDto.getCredentials());
		Tweet reply = tweetMapper.dtoToEntity(tweetRequestDto, user);
		reply.setInReplyTo(tweet);
		tweetRepository.saveAndFlush(tweet);

		// TODO: finish this
		processMentionsAndHashtags(reply);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
	}

	@Override
	public void addLikeToTweet(Long id, CredentialsDto credentialsDto) {
		Tweet tweet = validateTweet(id);
		User user = userService.validateUser(credentialsDto);

		if (!user.getLikes().contains(tweet)) {
			user.getLikes().add(tweet);
		}

		if (!tweet.getUsersWhoLike().contains(user)) {
			tweet.getUsersWhoLike().add(user);
		}

		tweetRepository.saveAndFlush(tweet);
		userRepository.saveAndFlush(user);
		return;
	}

	@Override
	public List<HashtagDto> getAllTagsFromTweet(Long id) {
		Tweet tweet = validateTweet(id);

		return hashtagMapper.entitiesToDtos(tweet.getTweetHashtags());
	}

	private Tweet validateTweet(Long id) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (tweet.isEmpty()) {
			throw new NotFoundException("Tweet with id " + id + " does not exist or is deleted.");
		}
		return tweet.get();
	}

	// TODO: Break into smaller methods
	private void processMentionsAndHashtags(Tweet tweet) {
		List<String> mentions = new ArrayList<>();
		List<String> hashtags = new ArrayList<>();

		String[] words = tweet.getContent().split(" ");

		for (String word : words) {
			// remove punctuation at beg. and end of word
			word = word.replaceAll("^[^a-zA-Z0-9@#]+|[^a-zA-Z0-9@#]+$", "");
			if (word.startsWith("@") && word.length() > 1) {
				mentions.add(word.substring(1));
			} else if (word.startsWith("#") && word.length() > 1) {
				hashtags.add(word.substring(1));
			}
		}

		for (String username : mentions) {
			// handle
		}

		for (String hashtag : hashtags) {
			Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(hashtag);
			if (optionalHashtag.isPresent()) {
				Hashtag updatedHashtag = optionalHashtag.get();
				List<Tweet> hashtagTweets = updatedHashtag.getTweets();
				hashtagTweets.add(tweet);
				updatedHashtag.setTweets(hashtagTweets);
//				updatedHashtag.setLastUsed(new Timestamp(now));
				hashtagRepository.saveAndFlush(updatedHashtag);
			} else {
				Hashtag newHashtag = new Hashtag();
				newHashtag.setTweets(new ArrayList<>(List.of(tweet)));
//				newHashtag.setLastUsed(new Timestamp(now));
				hashtagRepository.saveAndFlush(newHashtag);
			}

		}
//		tweet.setTweetHashtags(hashtags);

	}


}
