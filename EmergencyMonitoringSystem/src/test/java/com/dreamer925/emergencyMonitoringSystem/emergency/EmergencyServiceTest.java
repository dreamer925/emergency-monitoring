package com.dreamer925.emergencyMonitoringSystem.emergency;

import com.dreamer925.emergencyMonitoringSystem.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmergencyServiceTest {

    @Mock
    private EmergencyRepository repository;
    @InjectMocks
    private EmergencyService underTest;
    private final Emergency emergency = new Emergency(
            "Extreme fire",
            new EmergencyLocation("Ukraine", "Kharkiv"),
            LocalDateTime.now(),
            Type.Fire);
    private final EmergencyRequest emergencyRequest = new EmergencyRequest(
            "Extreme fire in Kharkiv",
            "Ukraine", "Kharkiv",
            LocalDateTime.now(),
            Type.Fire
    );
    @Mock
    private User user;

    @Test
    void findsEmergencyById() {
        Integer id = emergency.getId();

        underTest.findEmergencyById(id);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(repository).findById(argumentCaptor.capture());
        assertEquals(id, argumentCaptor.getValue());
    }

    @Test
    void getAllEmergencies() {
        underTest.getAllEmergencies();
        verify(repository).findAll();
    }

    @Test
    void findsEmergencyByLocation() {
        String location = emergency.getLocation().getCity();

        underTest.findEmergencyByLocation(location);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findByLocation(argumentCaptor.capture());
        assertEquals(location, argumentCaptor.getValue());
    }

    @Test
    void findsEmergencyByDate() {
        LocalDateTime date = emergency.getDate();
        underTest.findEmergencyByDate(date);

        ArgumentCaptor<LocalDateTime> argumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByDate(argumentCaptor.capture());
        assertEquals(date, argumentCaptor.getValue());
    }

    @Test
    void findsEmergencyByType() {
        Type type = emergency.getType();

        underTest.findEmergencyByType(type);

        ArgumentCaptor<Type> argumentCaptor = ArgumentCaptor.forClass(Type.class);
        verify(repository).findByType(argumentCaptor.capture());
        assertEquals(type, argumentCaptor.getValue());
    }

    @Test
    void findsEmergencyByName() {
        String name = emergency.getName();

        underTest.findEmergencyByName(name);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findAllByNameContainingIgnoreCaseOrderByDateDesc(argumentCaptor.capture());
        assertEquals(name, argumentCaptor.getValue());
    }

    @Test
    void savesEmergency() {

        given(repository.existsByNameAndDateAndType(emergencyRequest.name(), emergencyRequest.date(),
                        emergencyRequest.type())).willReturn(false);

        given(repository.existsById(emergency.getId())).willReturn(true);

        boolean saved = underTest.saveEmergency(emergencyRequest, user);

        assertThat(saved).isTrue();

        verify(repository).save(any(Emergency.class));
        verify(user).addEmergency(any(Emergency.class));
    }

    @Test
    void doesNotSaveEmergencyWhenSimilarExists() {

        given(repository.existsByNameAndDateAndType(emergencyRequest.name(), emergencyRequest.date(),
                emergencyRequest.type())).willReturn(true);

        boolean saved = underTest.saveEmergency(emergencyRequest, user);

        assertThat(saved).isFalse();

        verify(repository, never()).save(any(Emergency.class));
        verify(user, never()).addEmergency(any(Emergency.class));
    }

    @Test
    void saveEmergencyThrowsException() {
        given(repository.existsByNameAndDateAndType(emergencyRequest.name(), emergencyRequest.date(),
                emergencyRequest.type())).willReturn(false);

        given(repository.existsById(emergency.getId())).willReturn(false);

        assertThrows(ResponseStatusException.class, () -> underTest.saveEmergency(emergencyRequest, user));

        verify(user, never()).addEmergency(any(Emergency.class));
    }

    @Test
    void updatesEmergency() {
        given(repository.getReferenceById(emergency.getId())).willReturn(emergency);

        underTest.updateEmergency(emergency.getId(), emergencyRequest);

        assertEquals(emergency.getName(), emergencyRequest.name());
    }

    @Test
    void deletesEmergencyById() {
        given(repository.findById(1)).willReturn(Optional.of(emergency));

        underTest.deleteEmergencyById(1, user);

        verify(user).deleteEmergency(emergency);
        verify(repository).deleteById(1);
    }

    @Test
    void doesNotDeleteEmergencyIfItDoesNotExistAndThrowsException() {
        given(repository.findById(1)).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> underTest.deleteEmergencyById(1, user));

        verify(user, never()).deleteEmergency(any(Emergency.class));
        verify(repository, never()).deleteById(1);
    }
}
