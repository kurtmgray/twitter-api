package com.cooksys.spring_assessment.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User_Account")
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
    private List<User> following;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

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
    private List<Tweet> likes;

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
    private List<Tweet> mentions;

}
