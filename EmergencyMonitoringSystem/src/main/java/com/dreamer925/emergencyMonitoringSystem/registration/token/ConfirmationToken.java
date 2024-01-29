package com.dreamer925.emergencyMonitoringSystem.registration.token;

import com.dreamer925.emergencyMonitoringSystem.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    @Id
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime expires;
    private LocalDateTime confirmed;
    @NotNull
    @ManyToOne
    @JoinColumn
    private User user;

    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expires, User user) {
        this.token = token;
        this.created = created;
        this.expires = expires;
        this.user = user;
    }
}

