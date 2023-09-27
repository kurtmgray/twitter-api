package com.cooksys.spring_assessment.dtos;

import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.repositories.TweetRepository;
import com.cooksys.spring_assessment.exceptions.NotFoundException;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

import lombok.Getter;

@NoArgsConstructor
@Data
public class ContextDto {

	private TweetResponseDto target;
	private List<TweetResponseDto> before;
	private List<TweetResponseDto> after;
	
	
	// private static TweetRepository tweetRepository;
	
	// public ContextDto(Tweet _target, TweetRepository _tweetRepository) {
	// 	tweetRepository = _tweetRepository;
		
	// 	Comparator<Tweet> postedDateSorter = (t1, t2) -> t1.getPosted().compareTo(t2.getPosted());
		
	// 	target = _target;
	// 	before = createBefore(target, new TreeSet<Tweet>(postedDateSorter));
	// 	after = createAfter(target, new TreeSet<Tweet>(postedDateSorter));
	// }
	
	// public ContextDto(Tweet _target) throws NotFoundException {
	// 	if (tweetRepository == null) {
	// 		throw new NotFoundException("No tweet repository found.");
	// 	}
		
	// 	Comparator<Tweet> postedDateSorter = (t1, t2) -> t1.getPosted().compareTo(t2.getPosted());
		
	// 	target = _target;
	// 	before = createBefore(target, new TreeSet<Tweet>(postedDateSorter));
	// 	after = createAfter(target, new TreeSet<Tweet>(postedDateSorter));
	// }
	
	// private Tweet target;
	
	// private SortedSet<Tweet> before;
	
	// private SortedSet<Tweet> after;
	
	// SortedSet<Tweet> createBefore(Tweet _target, SortedSet<Tweet> _before) {
	// 	while (_target.getInReplyTo() != null) {
	// 		_target = _target.getInReplyTo();
	// 		_before.add(_target);
	// 	}
		
	// 	return _before;
	// }
	
	// SortedSet<Tweet> createAfter(Tweet _target, SortedSet<Tweet> _after) {
	// 	if (_target == null) {
	// 		return _after;
	// 	}
		
	// 	Set<Tweet> replies = tweetRepository.findByInReplyTo(_target).get();
		
	// 	if (replies == null || replies.isEmpty()) {
	// 		return _after;
	// 	}
		
	// 	for (Tweet t : replies) {
	// 		_after.add(t);
	// 		return createAfter(t, _after);
	// 	}
	// 	return createAfter(null, null);
	// }
}


