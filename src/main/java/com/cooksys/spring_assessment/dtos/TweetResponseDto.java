package com.cooksys.spring_assessment.dtos;


import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;

    @NonNull
    private UserResponseDto author;

    @NonNull
    private Timestamp posted;

    private String content;

    private TweetResponseDto inReplyTo;

    private TweetResponseDto repostOf;

}
