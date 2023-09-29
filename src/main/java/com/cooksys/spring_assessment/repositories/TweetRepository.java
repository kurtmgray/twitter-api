package com.cooksys.spring_assessment.repositories;

import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;

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

    Optional<Set<Tweet>> findByInReplyToAndDeletedFalse(Tweet target);
    
    Optional<List<Tweet>> findByAuthorAndDeletedFalse(User author);
    
    List<Tweet> findByMentionedUsersAndDeletedFalse(User user);

}
