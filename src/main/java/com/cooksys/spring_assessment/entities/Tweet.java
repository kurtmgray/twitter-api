package com.cooksys.spring_assessment.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author")
    @NonNull
    private User author;

    @CreationTimestamp
    final private Timestamp posted;

    private boolean deleted = false;

    private String content;

    @ManyToOne
    @JoinColumn(name = "inReplyTo")
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "repostOf")
    private Tweet repostOf;

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = {
                    @JoinColumn(name = "tweet_id"),
            },
            inverseJoinColumns =  {
                    @JoinColumn(name = "user_id"),
            }
    )
    private Set<User> mentionedUsers;

    @ManyToMany(mappedBy = "likes")
    private Set<User> usersWhoLike;

    @ManyToMany(mappedBy = "mentions")
    private Set<User> usersWhoMention;

//    @ManyToMany
//    @JoinTable(
//            name = "tweet_hashtag",
//            joinColumns = @JoinColumn(name = "tweet_id"),
//            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
//    )
//    private Set<Hashtag> tweetHashtags;
}
