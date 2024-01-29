package com.dreamer925.emergencyMonitoringSystem.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Email @Pattern(regexp=".+@.+\\..+", message="Email address is not valid.") String username,
        @NotBlank @Length(min = 8) String password) { }
