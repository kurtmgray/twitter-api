package com.cooksys.spring_assessment.dtos;


import java.sql.Timestamp;

import com.cooksys.spring_assessment.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class TweetResponseDto {
    

  

    private Integer author;

    private Timestamp posted;

    private String content;

    private Tweet inReplyOf;

    private Tweet repostOf;


}
