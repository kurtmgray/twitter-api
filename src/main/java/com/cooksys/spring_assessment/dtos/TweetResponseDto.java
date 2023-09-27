package com.cooksys.spring_assessment.dtos;


import java.sql.Timestamp;

import com.cooksys.spring_assessment.entities.Tweet;

import com.cooksys.spring_assessment.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor
@Data
public class TweetResponseDto {

    @NonNull
    private UserResponseDto author;

    @NonNull
    private Timestamp posted;

    private String content;

    private TweetResponseDto inReplyOf;

    private TweetResponseDto repostOf;

}
