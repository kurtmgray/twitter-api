package com.cooksys.spring_assessment.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Data
public class ProfileDto {

    private String firstName;

    private String lastName;

    @NonNull
    private String email;

    private String phone;

}
