package com.dreamer925.emergencyMonitoringSystem.registration.token;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository repository;

    @InjectMocks
    private ConfirmationTokenService underTest;

    @Mock
    private ConfirmationToken confirmationToken;

    @Test
    void savesConfirmationToken() {
        underTest.saveConfirmationToken(confirmationToken);

        verify(repository).save(confirmationToken);
    }

    @Test
    void canGetConfirmationToken() {
        String token = anyString();
        underTest.getConfirmationToken(token);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByToken(argumentCaptor.capture());
        assertEquals(token, argumentCaptor.getValue());
    }

    @Test
    void setsConfirmed() {
        String token = anyString();
        given(repository.findByToken(token)).willReturn(Optional.of(confirmationToken));

        underTest.setConfirmed(token);

        verify(confirmationToken).setConfirmed(any(LocalDateTime.class));
    }

    @Test
    void setConfirmedThrowsException() {
        String token = anyString();
        given(repository.findByToken(token)).willReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> underTest.setConfirmed(token));
    }
}