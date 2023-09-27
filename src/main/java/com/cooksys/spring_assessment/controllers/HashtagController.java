package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.entities.Hashtag;
import com.cooksys.spring_assessment.services.HashtagService;
import com.cooksys.spring_assessment.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;
    private final TweetService tweetService;

//    @GetMapping
//    public ResponseEntity<List<Hashtag>> getAllHashTags() {return ResponseEntity<>(hashtagService.getAllHastags(), HttpStatus.OK)};
//
//    @GetMapping("/{label}")
//    public ResponseEntity<List<TweetResponseDto>> getAllTweetsWithLabel(@PathVariable String label) {return ResponseEntity<>(tweetService.getAllTweetsWithLabel(label), HttpStatus.OK)};
}
