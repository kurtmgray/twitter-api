package com.cooksys.spring_assessment.controllers;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponseDto[]> getUsers() {
      return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/@{username}")
    public ResponseEntity<UserResponseDto> getSpecificUser(@PathVariable String username) {
      return new ResponseEntity<>(userService.getSpecificUser(username),  HttpStatus.OK);
    }

    @PatchMapping("/@{username}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String username) {
      return new ResponseEntity<>(userService.updateUser(userRequestDto, username), HttpStatus.OK);
    }

    @DeleteMapping("/@{username}")
    public ResponseEntity<UserResponseDto> deleteUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
      return new ResponseEntity<>((userService.deleteUser(credentialsDto, username)), HttpStatus.OK);
    }

    @PostMapping("/@{username}/follow")
    public ResponseEntity<?> follow(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
      userService.follow(credentialsDto, username);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/@{username}/unfollow")
    public ResponseEntity<?> unfollow(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
      userService.unfollow(credentialsDto, username);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/@{username}/feed")
    public ResponseEntity<List<TweetResponseDto>> getFeed(@PathVariable String username) {
      return new ResponseEntity<>(userService.getFeed(username), HttpStatus.OK);
    }

    @GetMapping("/@{username}/tweets")
    public ResponseEntity<List<TweetResponseDto>> getTweets(@PathVariable String username) {
      return new ResponseEntity<>(userService.getTweets(username), HttpStatus.OK);
    }

    @GetMapping("/@{username}/mentions")
    public ResponseEntity<List<TweetResponseDto>> getMentions(@PathVariable String username) {
      return new ResponseEntity<>(userService.getMentions(username), HttpStatus.OK);
    }

    @GetMapping("/@{username}/followers")
    public ResponseEntity<List<UserResponseDto>> getFollowers(@PathVariable String username) {
      return new ResponseEntity<>(userService.getFollowers(username), HttpStatus.OK);
    }

    @GetMapping("/@{username}/following")
    public ResponseEntity<List<UserResponseDto>> getFollowing(@PathVariable String username) {
      return new ResponseEntity<>(userService.getFollowing(username), HttpStatus.OK);
    }

}