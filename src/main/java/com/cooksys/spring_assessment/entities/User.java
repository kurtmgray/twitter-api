package com.cooksys.spring_assessment.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Embedded;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="Account")
@RequiredArgsConstructor
@Data
public class User {

  @Id
  @GeneratedValue
  private Long id;
  
  final private Timestamp joined;
  
  private boolean deleted = false;
  
  @Embedded
  private Profile profile;

//@Embedded
//private Credential credential;
  
  @OneToMany
  @JoinColumn(name="following_id")
  private List<User> followers;

  @OneToMany
  @JoinColumn(name="follower_id")
  private List<User> following;
  
//	@OneToMany
//  @JoinColumn(name="author")
//  private List<Tweet> tweets;
//
//  @OneToMany
//	@JoinColumn(name="tweet_id")
//  private List<Tweet> likes;
//  
//  @OneToMany
//	@JoinColumn(name="tweet_id")
//  private List<Tweet> mentions;
  
}
