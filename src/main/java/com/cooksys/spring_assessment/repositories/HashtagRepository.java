package com.cooksys.spring_assessment.repositories;

import com.cooksys.spring_assessment.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByLabel(String hashtag);
    
}