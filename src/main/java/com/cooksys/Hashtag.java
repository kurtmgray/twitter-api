package com.cooksys;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
    
    @Id
    @GeneratedValue
    private Long id;


    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;


    @ManyToMany(mappedBy = "hashtags")
    private List<Tweet> tweets;



}
