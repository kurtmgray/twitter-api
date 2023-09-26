package com.cooksys.spring_assessment.dtos;

import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

    private Credentials credentials;

    private Profile profile;

}
