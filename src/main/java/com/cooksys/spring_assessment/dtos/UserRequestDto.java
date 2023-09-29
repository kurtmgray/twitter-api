package com.cooksys.spring_assessment.dtos;

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
