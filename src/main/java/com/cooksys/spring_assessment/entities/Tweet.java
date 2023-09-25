package com.cooksys.spring_assessment.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @CreationTimestamp
    private Timestamp posted;

    private boolean deleted;
    private String content;

    @ManyToOne
    @JoinColumn(name = "in_reply_to_id")
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "repost_of_id")
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

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> tweetHashtags;
}
