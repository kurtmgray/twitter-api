package com.cooksys.spring_assessment.repositories;

import com.cooksys.spring_assessment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndCredentialsPassword(String username, String password);

    List<User> findByLikesIdAndDeletedFalse(Long tweetId);

    Optional<User> findByCredentialsUsername(String username);
}
