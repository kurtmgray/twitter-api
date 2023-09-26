package com.cooksys.spring_assessment.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

    @NonNull
    private String username;

    @NonNull
    private String password;

}
