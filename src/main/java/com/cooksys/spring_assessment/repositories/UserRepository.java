package com.cooksys.spring_assessment.repositories;

import com.cooksys.spring_assessment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndCredentialsPassword(String username, String password);

    Optional<User> findByCredentialsUsername(String username);

    Optional<User> findByCredentialsPassword(String password);
}
