package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.dtos.HashtagDto;
import com.cooksys.spring_assessment.services.HashtagService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public ResponseEntity<List<HashtagDto>> getAllHashTags() {
    	return new ResponseEntity<>(hashtagService.getAllHashtags(), HttpStatus.OK);
    }

    @GetMapping("/{label}")
    public ResponseEntity<List<TweetResponseDto>> getAllTweetsWithLabel(@PathVariable String label) {
    	return new ResponseEntity<>(hashtagService.getAllTweetsWithLabel(label), HttpStatus.OK);
    }

}
