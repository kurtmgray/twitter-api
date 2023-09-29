package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotFoundException;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.UserService;
import com.cooksys.spring_assessment.services.ValidateService;
import com.cooksys.spring_assessment.repositories.TweetRepository;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.mappers.UserMapper;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidateService validateService;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;


    @Override
    public UserResponseDto[] getAllUsers() {
        System.out.println("hi");
        List<User> l = userRepository.findAll();
        
        l.removeIf(n -> (n.isDeleted()));

        User[] ret = new User[l.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l.get(i);
        }
        


        System.out.println("everything went right up to return ");
        return (userMapper.uentitiesToDtos(ret));
       
        
    }


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        

        if (userRequestDto == null || userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null || 
        userRequestDto.getProfile() == null ||userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("invalid input");
        }
        

        User user = userMapper.urequestToEntity(userRequestDto);
        Optional<User> o = userRepository.findByCredentialsUsername(user.getCredentials().getUsername());
        if (o.isPresent()) {
            if (o.get().isDeleted()) {
                o.get().setDeleted(false);
                userRepository.saveAndFlush(o.get());
            }
            else {
                throw new BadRequestException("user already there");
            }
        }
        

        

        userRepository.saveAndFlush(user);
        

       
        return userMapper.uentityToDto(user);
    }


    @Override
    public UserResponseDto getSpecificUser(String username) {
        if (username == null) {
            throw new BadRequestException("bad request");
        }
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        System.out.println(username);
            
        if (!u.isPresent()) {
            throw new NotFoundException("not found");
        }
        System.out.println(u.get().getTweets().size());
        return userMapper.uentityToDto(u.get());
    }


    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String username) {

        if (username == null) {
            throw new BadRequestException("null");
        }

        if (userRequestDto == null || userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null || 
        userRequestDto.getProfile() == null ||userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("invalid input");
        }
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new NotFoundException("not found");
        }
        User user = u.get();
        if (user.isDeleted()) {
            throw new NotFoundException("not found");
        }

        User check = userMapper.urequestToEntity(userRequestDto);

        Optional<User> o = userRepository.findByCredentialsUsernameAndCredentialsPassword(check.getCredentials().getUsername(), 
        check.getCredentials().getPassword());

        if (o.isEmpty()) {
            throw new BadRequestException("invalid credentials");
        }
        user.getCredentials().setUsername(username);
        userRepository.saveAndFlush(user);
        return userMapper.uentityToDto(user);
    }


    @Override
    public UserResponseDto deleteUser(CredentialsDto credentialsDto, String username) {
        if (username == null) {
            throw new BadRequestException("null");
        }
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new NotFoundException("not found");
        }
        User user = u.get();

        if (user.isDeleted()) {
            throw new BadRequestException("user already deleted");
        }
        user.setDeleted(true);
        System.out.println(user.isDeleted());
        userRepository.saveAndFlush(user);
        return userMapper.uentityToDto(user);
    }


    @Override
    public void follow(CredentialsDto credentialsDto, String username) {
        if (credentialsDto == null || credentialsDto.getPassword() == null || credentialsDto.getUsername() == null || username == null) {
            throw new BadRequestException("bad request");
        }


        Optional<User> u = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentialsDto.getUsername(), credentialsDto.getPassword());

        if (!u.isPresent()) {
            throw new BadRequestException("not there follower");
        }

        User user1 = u.get();
        Optional<User> uFollow = userRepository.findByCredentialsUsername(username);

        if (!uFollow.isPresent() || uFollow.get().isDeleted()) {
            throw new BadRequestException("not there following");
        }

        User user2 = uFollow.get();

        if (user1.getFollowing().contains(user2)) {
            throw new BadRequestException("user already following");
        }
        user1.getFollowing().add(user2);
        user2.getFollowers().add(user1);

        

        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);
    }


    @Override
    public void unfollow(CredentialsDto credentialsDto, String username) {
        if (credentialsDto == null || credentialsDto.getPassword() == null || credentialsDto.getUsername() == null || username == null) {
            throw new BadRequestException("bad request");
        }
         Optional<User> u = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }

        User user1 = u.get();
        Optional<User> uFollow = userRepository.findByCredentialsUsername(username);

        if (!uFollow.isPresent() || uFollow.get().isDeleted()) {
            throw new BadRequestException("not there");
        }

        User user2 = uFollow.get();


        user1.getFollowing().remove(user2);
        user2.getFollowers().remove(user1);

        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);
    }


    @Override
    public List<TweetResponseDto> getFeed(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }

        User user = u.get();
        List<Tweet> tweets = user.getTweets();
        for (User uf : user.getFollowers()) {
            tweets.addAll(uf.getTweets());
        }
        tweets.removeIf(n -> (n.isDeleted()));
        tweets.sort((t2, t1)-> t1.getPosted().compareTo(t2.getPosted())); 
        return tweetMapper.tentitiesToDtos(tweets);


    }


    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        List<Tweet> tweets = user.getTweets();
        tweets.removeIf(n -> (n.isDeleted()));
        tweets.sort((t1, t2)-> t1.getPosted().compareTo(t2.getPosted())); 
        return tweetMapper.tentitiesToDtos(tweets);
    }


    @Override
    public List<TweetResponseDto> getMentions(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        List<Tweet> l = tweetRepository.findAll();
        for (Tweet t: l) {
            if (!t.getMentionedUsers().contains(user)) {
                l.remove(t);
            }
        }
        l.sort((t1, t2)-> t1.getPosted().compareTo(t2.getPosted())); 
        return tweetMapper.tentitiesToDtos(l);
        
    }


    @Override
    public List<UserResponseDto> getFollowers(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        System.out.println("He has : " + user.getFollowers().size());
        return userMapper.uLEntitiesToDtos(user.getFollowers());
        
    }


    @Override
    public List<UserResponseDto> getFollowing(String username) {

        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        return userMapper.uLEntitiesToDtos(user.getFollowing());
    }

    

    





   
    
}
