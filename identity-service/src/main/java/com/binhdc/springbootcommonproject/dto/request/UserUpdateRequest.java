package com.binhdc.springbootcommonproject.dto.request;

import com.binhdc.springbootcommonproject.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    private String password;
    private String firstName;
    private String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

    List<String> roles;
}