package com.dreamer925.emergencyMonitoringSystem.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @InjectMocks
    private UserService underTest;

    @Test
    void loadUserByUsername() {
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        

    }

    @Test
    void loadUserByUsernameThrowsException() {
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> underTest.loadUserByUsername(anyString()));
    }
}