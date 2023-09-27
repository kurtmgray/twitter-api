package com.cooksys.spring_assessment.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author")
    @NonNull
    private User author;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Timestamp posted;

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
    private List<User> mentionedUsers;

    @ManyToMany(mappedBy = "likes")
    private List<User> usersWhoLike;

    @ManyToMany(mappedBy = "mentions")
    private List<User> usersWhoMention;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> tweetHashtags;
}
