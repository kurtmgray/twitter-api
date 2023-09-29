package com.cooksys.spring_assessment.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class UserResponseDto {

    @NonNull
    private String username;

    @NonNull
    private ProfileDto profile;

    @NonNull
    private Timestamp joined;

}
