package com.binhdc.springbootcommonproject.dto.request;

import com.binhdc.springbootcommonproject.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password;

    private String firstName;
    private String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    private LocalDate dob;
}