package com.dreamer925.emergencyMonitoringSystem.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(
        @NotBlank String name,
        @Email @Pattern(regexp=".+@.+\\..+", message="Email address is not valid.") String email,
        @NotBlank @Length(min = 8) String password
) {
}
