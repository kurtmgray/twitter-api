package com.cooksys.spring_assessment.dtos;

import com.cooksys.spring_assessment.entities.Credentials;
import com.cooksys.spring_assessment.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Data
public class UserRequestDto {

    @NonNull
    private CredentialsDto credentials;

    @NonNull
    private ProfileDto profile;

}
