package com.cooksys.spring_assessment.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class HashtagDto {

    @NonNull
    private String label;

    @NonNull
    private Timestamp firstUsed;

    @NonNull
    private Timestamp lastUsed;

}
