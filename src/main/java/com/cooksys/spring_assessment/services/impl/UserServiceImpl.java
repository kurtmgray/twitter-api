package com.cooksys.spring_assessment.services.impl;

import com.cooksys.spring_assessment.dtos.CredentialsDto;
import com.cooksys.spring_assessment.dtos.TweetResponseDto;
import com.cooksys.spring_assessment.dtos.UserRequestDto;
import com.cooksys.spring_assessment.dtos.UserResponseDto;
import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Tweet;
import com.cooksys.spring_assessment.entities.User;
import com.cooksys.spring_assessment.exceptions.BadRequestException;
import com.cooksys.spring_assessment.exceptions.NotAuthorizedException;
import com.cooksys.spring_assessment.exceptions.NotFoundException;
import com.cooksys.spring_assessment.repositories.UserRepository;
import com.cooksys.spring_assessment.services.UserService;
import com.cooksys.spring_assessment.repositories.TweetRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.cooksys.spring_assessment.mappers.CredentialsMapper;
import com.cooksys.spring_assessment.mappers.TweetMapper;
import com.cooksys.spring_assessment.mappers.UserMapper;
import com.cooksys.spring_assessment.mappers.ProfileMapper;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;

	private final Comparator<Tweet> postedDateComparator = (t1, t2) -> t2.getPosted().compareTo(t1.getPosted());

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
        return (userMapper.entitiesToDtosA(ret));
       
        
    }


    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto == null || userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null || 
        userRequestDto.getProfile() == null ||userRequestDto.getProfile().getEmail() == null || userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("invalid input");
        }
        User user = userMapper.requestToEntity(userRequestDto);
        Optional<User> o = userRepository.findByCredentialsUsername(user.getCredentials().getUsername());
        if (o.isPresent()) {
            System.out.println("checking if readded");
            User m = o.get();
            System.out.println("checking if get");
            if (m.isDeleted()) {
                System.out.println("readded");
                m.setDeleted(false);
                userRepository.saveAndFlush(m);
                return userMapper.userToUserResponseDto(m);
            }
            System.out.println("chcecking if error");
            throw new BadRequestException("can't readd again");
        }
        System.out.println("executed");
        userRepository.saveAndFlush(user); 
        return userMapper.userToUserResponseDto(user);
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
        return userMapper.userToUserResponseDto(u.get());
    }


    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String username) {
        if (userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getPassword() == null || 
        userRequestDto.getCredentials().getUsername() == null) {
            throw new BadRequestException("empty");
        }
        Optional<User> u1= userRepository.findByCredentialsUsernameAndCredentialsPassword(userRequestDto.getCredentials().getUsername(), 
        userRequestDto.getCredentials().getPassword());

        Optional<User> u2 = userRepository.findByCredentialsUsername(username);

        if (u1.isEmpty() || u2.isEmpty()) {
            throw new BadRequestException("empty");
        }

        User user1 = u1.get();
        User user2 = u2.get();

        if (!user1.getCredentials().getPassword().equals(user2.getCredentials().getPassword()) && 
        user1.getCredentials().getUsername().equals(user2.getCredentials().getUsername())) {
            throw new BadRequestException("credentials dont match");
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("credentials dont match");
        }

        if (userRequestDto.getProfile().getEmail() == null) {
            return userMapper.userToUserResponseDto(user2);
        }
        
        if (userRequestDto.getProfile().getEmail() != null) {
           user2.getProfile().setEmail(userRequestDto.getProfile().getEmail());
       }
//        if (userRequestDto.getProfile().getFirstName() != null) {
//            user2.getProfile().setFirstName(userRequestDto.getProfile().getFirstName());
//        }
//        if (userRequestDto.getProfile().getLastName() != null) {
//            user2.getProfile().setLastName(userRequestDto.getProfile().getLastName());
//        }
//        if (userRequestDto.getProfile().getPhone() != null) {
//            user2.getProfile().setPhone(userRequestDto.getProfile().getPhone());
//        }
        user2.setProfile(profileMapper.dtoToEntiy(userRequestDto.getProfile()));
        
        userRepository.saveAndFlush(user2);
        return userMapper.userToUserResponseDto(user2);
        
    }


    @Override
    public UserResponseDto deleteUser(CredentialsDto credentialsDto, String username) {
        if (username == null || credentialsDto.getUsername() == null) {
            throw new BadRequestException("null");
        }
        Optional<User> u = userRepository.findByCredentialsUsernameAndCredentialsPassword(username, credentialsDto.getPassword());
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
        return userMapper.userToUserResponseDto(user);
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
         Optional<User> u = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentialsDto.getUsername(), credentialsDto.getPassword());

        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }

        User user1 = u.get();
        Optional<User> uFollow = userRepository.findByCredentialsUsername(username);

        if (!uFollow.isPresent() || uFollow.get().isDeleted() || !uFollow.get().getFollowers().contains(user1)) {
            throw new NotFoundException("user you are attempting to unfollow does not exist, has been deleted, or is not currently being followed by you");
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
        tweets.removeIf(n -> (n.getDeleted()));
        tweets.sort(postedDateComparator); 
        return tweetMapper.entitiesToDtos(tweets);


    }


    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        List<Tweet> tweets = tweetRepository.findByAuthorAndDeletedFalse(user).get();
        //tweets.removeIf(n -> (n.getDeleted()));
        tweets.sort(postedDateComparator); 
        return tweetMapper.entitiesToDtos(tweets);
    }


    @Override
    public List<TweetResponseDto> getMentions(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        List<Tweet> l = tweetRepository.findByMentionedUsersAndDeletedFalse(user);
//        for (Tweet t: l) {
//            if (!t.getMentionedUsers().contains(user)) {
//                l.remove(t);
//            }
//        }
        l.sort(postedDateComparator); 
        return tweetMapper.entitiesToDtos(l);
        
    }


    @Override
    public List<UserResponseDto> getFollowers(String username) {
        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent() || u.get().isDeleted()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        System.out.println("He has : " + user.getFollowers().size());
        return userMapper.entitiesToDtos(userRepository.findByFollowingAndDeletedFalse(user));
        
    }


    @Override
    public List<UserResponseDto> getFollowing(String username) {

        Optional<User> u = userRepository.findByCredentialsUsername(username);
        if (!u.isPresent() || u.get().isDeleted()) {
            throw new BadRequestException("not there");
        }
        User user = u.get();
        return userMapper.entitiesToDtos(userRepository.findByFollowersAndDeletedFalse(user));
    }


    @Override
    public User validateUser(CredentialsDto credentialsDto) throws BadRequestException, NotAuthorizedException {
        if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("Both username and password are required.");
        }
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(), credentials.getPassword());

        if (optionalUser.isEmpty()) {
            throw new NotAuthorizedException("Invalid credentials.");
        }

        return optionalUser.get();
    }
}
