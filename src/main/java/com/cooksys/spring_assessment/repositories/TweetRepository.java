package com.cooksys.spring_assessment.repositories;

import com.cooksys.spring_assessment.entities.Tweet;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
	
	Optional<Set<Tweet>> findByInReplyTo(Tweet target);

    List<Tweet> findByDeletedFalse(Sort sort);

    Optional<Tweet> findByIdAndDeletedFalse(Long id);
    
    Optional<List<Tweet>> findByTweetHashtagsAndDeletedFalse(String label);
    
    Optional<Set<Tweet>> findByInReplyToAndDeletedFalse(Tweet target);
}
