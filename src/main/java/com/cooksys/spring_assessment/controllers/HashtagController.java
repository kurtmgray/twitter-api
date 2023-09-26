package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.services.TweetService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

//    private final HashtagService hashtagService;
    private final TweetService tweetService;

//    @GetMapping
//    public ResponseEntity<List<HashtagDto>> getAllHashTags() {return ResponseEntity<>(hashtagService.getAllHastags(), HttpStatus.OK)};
//
//    @GetMapping("/{label}")
//    public ResponseEntity<List<TweetDto>> getAllTweetsWithLabel(@PathVariable String label) {return ResponseEntity<>(tweetService.getAllTweetsWithLabel(label), HttpStatus.OK)};
}
