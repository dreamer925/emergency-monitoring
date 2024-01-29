package com.dreamer925.emergencyMonitoringSystem.registration.token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getConfirmationToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    @Transactional
    public void setConfirmed(String token) {
        ConfirmationToken toUpdate = confirmationTokenRepository.findByToken(token).orElseThrow(() ->
                        new IllegalStateException(
                                "Token " + token + " not found.")
        );
        toUpdate.setConfirmed(LocalDateTime.now());
    }
}
