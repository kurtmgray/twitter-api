package com.cooksys.spring_assessment.entities;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="User_Account")
@RequiredArgsConstructor
@Data
public class User {

  @Id
  @GeneratedValue
  private Long id;
  
  @CreationTimestamp
  final private Timestamp joined;
  
  private boolean deleted = false;
  
  @Embedded
  private Profile profile;

  @Embedded
  private Credentials credentials;
  
  @ManyToMany
  @JoinTable(
		  name="followers_following",
		  joinColumns = {
				  @JoinColumn(name = "follower_id")
		  },
		  inverseJoinColumns = {
				  @JoinColumn(name = "following_id")
		  }
	)
  private Set<User> following;
  
  @ManyToMany(mappedBy="following")
  private Set<User> followers;
  
  @OneToMany(mappedBy="author")
  private Set<Tweet> tweets;

  @ManyToMany
  @JoinTable(
	  name="user_likes",
	  joinColumns = {
			  @JoinColumn(name = "user_id")
	  },
	  inverseJoinColumns = {
			  @JoinColumn(name = "tweet_id")
	  }
	)
  private Set<Tweet> likes;
  
  @ManyToMany
  @JoinTable(
	  name="user_mentions",
	  joinColumns = {
			  @JoinColumn(name = "user_id")
	  },
	  inverseJoinColumns = {
			  @JoinColumn(name = "tweet_id")
	  }
	)
  private Set<Tweet> mentions;
  
}
