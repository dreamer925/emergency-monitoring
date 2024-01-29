package com.dreamer925.emergencyMonitoringSystem.emergency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EmergencyRequest(@NotBlank String name, String country,
                               String city, @NotNull LocalDateTime date, @NotNull Type type) {
}
