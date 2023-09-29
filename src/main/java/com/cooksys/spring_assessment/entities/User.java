package com.cooksys.spring_assessment.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="User_Account")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Timestamp joined;

    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = {
                    @JoinColumn(name = "follower_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "following_id")
            }
    )
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id")
            }
    )
    private List<Tweet> likes = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tweet_id")
            }
    )
    private List<Tweet> mentions = new ArrayList<>();

}
