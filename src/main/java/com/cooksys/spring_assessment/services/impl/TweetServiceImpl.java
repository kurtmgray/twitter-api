package com.cooksys.spring_assessment.services.impl;

//import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.dtos.*;
import com.cooksys.spring_assessment.entities.Hashtag;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotAuthorizedException;
import com.cooksys.spring_assessment.exceptions.NotFoundException;
import com.cooksys.spring_assessment.mappers.HashtagMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.mappers.UserMapper;
import com.cooksys.spring_assessment.repositories.HashtagRepository;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.TweetService;

import com.cooksys.spring_assessment.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;
	private final UserService userService;
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	
	private final Comparator<Tweet> postedDateComparator = (t1, t2) -> t2.getPosted().compareTo(t1.getPosted());

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
		Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
		tweet.setAuthor(user);

		processMentions(tweet);
		processHashtags(tweet);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
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
    }

	@Override
	public TweetResponseDto addTweetReply(Long id, TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getCredentials() == null) {
			throw new BadRequestException("Malformed request.");
		}

		Tweet tweet = validateTweet(id);
		User user = userService.validateUser(tweetRequestDto.getCredentials());
		Tweet reply = tweetMapper.dtoToEntity(tweetRequestDto);

		reply.setAuthor(user);
		reply.setInReplyTo(tweet);

		processMentions(reply);
		processHashtags(reply);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(reply));
	}

	@Override
	public TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto) {
		User user = userService.validateUser(credentialsDto);
		Tweet tweet = validateTweet(id);

		Tweet repost = new Tweet();
		repost.setAuthor(user);
		repost.setRepostOf(tweet);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}

	@Override
	public List<HashtagDto> getAllTagsFromTweet(Long id) {
		Tweet tweet = validateTweet(id);

		return hashtagMapper.entitiesToDtos(tweet.getTweetHashtags());
	}

	@Override
	public List<UserResponseDto> getAllUserLikesOfTweet(Long id) {
		Tweet tweet = validateTweet(id);

		return userMapper.entitiesToDtos(tweet.getUsersWhoLike());
	}

	@Override
	public List<TweetResponseDto> getAllRepostsOfTweet(Long id) {
		Tweet tweet = validateTweet(id);

		return tweetMapper.entitiesToDtos(tweet.getReposts());
	}

	@Override
	public List<UserResponseDto> getAllMentionsInTweet(Long id) {
		Tweet tweet = validateTweet(id);

		return userMapper.entitiesToDtos(tweet.getMentionedUsers());
	}

	@Override
	public List<TweetResponseDto> getDirectRepliesToTweet(Long id) {
		Tweet target = validateTweet(id);
		Optional<Set<Tweet>> getDirectReplies = tweetRepository.findByInReplyToAndDeletedFalse(target);
		if (!getDirectReplies.isEmpty()) {
			List<Tweet> directReplies = new ArrayList<Tweet>(getDirectReplies.get());
			return tweetMapper.entitiesToDtos(directReplies);
		}
		return new ArrayList<TweetResponseDto>();
	}
	
	@Override
	public ContextDto getContextToTweet(Long id) {
		Tweet target = validateTweet(id);
		
		ContextDto context = new ContextDto();
		context.setTarget(tweetMapper.entityToDto(target));
		context.setBefore(tweetMapper.entitiesToDtos(createContextBefore(target)));
		context.setAfter(tweetMapper.entitiesToDtos(new ArrayList<Tweet>((createContextAfter(target, new TreeSet<Tweet>(postedDateComparator))))));
		
		return context;
	}
	
	 private List<Tweet> createContextBefore(Tweet target) {
		List<Tweet> beforeTweets = new ArrayList<Tweet>();
	 	while (target.getInReplyTo() != null) {
	 		target = target.getInReplyTo();
	 		beforeTweets.add(target);
	 	}
	 	return beforeTweets;
	 }
	
	 private SortedSet<Tweet> createContextAfter(Tweet target, SortedSet<Tweet> afterTweets) {
		Optional<Set<Tweet>> getReplies = tweetRepository.findByInReplyTo(target);
		
		if (!getReplies.isEmpty()) {
			for (Tweet t : getReplies.get()) {
				if (!t.getDeleted()) {
					afterTweets.add(t);
				}
				createContextAfter(t, afterTweets);
			}
		}
		 return afterTweets;
	}

	private Tweet validateTweet(Long id) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (tweet.isEmpty()) {
			throw new NotFoundException("Tweet with id " + id + " does not exist or is deleted.");
		}
		return tweet.get();
	}

	private List<String> extractMetadataFromContent(String symbol, String content) {
		List<String> metadata = new ArrayList<>();
		String[] words = content.split(" ");

		for (String word : words) {
			// remove punctuation at beg. and end of word
			word = word.replaceAll("^[^a-zA-Z0-9@#]+|[^a-zA-Z0-9@#]+$", "");
			if (word.startsWith(symbol) && word.length() > 1) {
				metadata.add(word.substring(1));
			}
		}
		return metadata;
	}

	private void processMentions(Tweet tweet) {
		List<String> mentions = extractMetadataFromContent("@", tweet.getContent());
		List<User> mentionedUsers = new ArrayList<>();
		for (String username : mentions) {
			Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

			if (optionalUser.isPresent()) {
				User mentionedUser = optionalUser.get();
				mentionedUser.getMentions().add(tweetRepository.saveAndFlush(tweet));

				mentionedUsers.add(mentionedUser);
			} else {
				// handle where mention isn't a valid username
				System.out.println("Mention is not a valid username.");
			}
		}
		userRepository.saveAllAndFlush(mentionedUsers);
	}

	private void processHashtags(Tweet tweet) {
		List<String> hashtags = extractMetadataFromContent("#", tweet.getContent());
		List<Hashtag> hashtagEntities = new ArrayList<>();
		for (String label : hashtags) {
			Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(label);

			if (optionalHashtag.isPresent()) {
				Hashtag updatedHashtag = optionalHashtag.get();
				List<Tweet> hashtagTweets = updatedHashtag.getTweets();
				hashtagTweets.add(tweet);
				updatedHashtag.setTweets(hashtagTweets);
				updatedHashtag.setLastUsed(new Timestamp(System.currentTimeMillis()));

				hashtagRepository.saveAndFlush(updatedHashtag);
				hashtagEntities.add(updatedHashtag);
			} else {
				Hashtag newHashtag = new Hashtag();
				newHashtag.setLabel(label);
				newHashtag.setTweets(new ArrayList<>(List.of(tweet)));
				hashtagEntities.add(newHashtag);
				hashtagRepository.saveAndFlush(newHashtag);
			}

		}
		tweet.setTweetHashtags(hashtagEntities);
		tweetRepository.saveAndFlush(tweet);
	}

}
