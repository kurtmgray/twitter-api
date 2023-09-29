package com.cooksys.spring_assessment.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public boolean getDeleted() { return deleted; }

    private String content;

    @ManyToOne
    @JoinColumn(name = "inReplyTo")
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "repostOf")
    private Tweet repostOf;

    @ManyToMany(mappedBy = "mentions")
    private List<User> mentionedUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "likes")
    private List<User> usersWhoLike = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> tweetHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replies = new ArrayList<>();

    @OneToMany(mappedBy = "repostOf")
    private List<Tweet> reposts = new ArrayList<>();
}
