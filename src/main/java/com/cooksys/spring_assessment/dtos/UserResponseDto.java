package com.cooksys.spring_assessment.dtos;


import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class UserResponseDto {

    

    private String username;

    private String email;

    private Timestamp joined;

    
}
