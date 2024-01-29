package com.dreamer925.emergencyMonitoringSystem.emergency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class EmergencyRepositoryTest {

    @Autowired
    private EmergencyRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findsEmergencyByCountry() {
        String country = "Ukraine";
        Emergency emergency = new Emergency(
                "Extreme fire",
                new EmergencyLocation(country, "Kharkiv"),
                LocalDateTime.now(),
                Type.Fire
        );

        underTest.save(emergency);
        assertEquals(emergency, underTest.findByLocation(country).get(0));
    }

    @Test
    void findsEmergencyByCity() {
        String city = "Kharkiv";
        Emergency emergency = new Emergency(
                "Extreme fire",
                new EmergencyLocation("Ukraine", city),
                LocalDateTime.now(),
                Type.Fire
        );

        underTest.save(emergency);
        assertEquals(emergency, underTest.findByLocation(city).get(0));
    }

    @Test
    void findsAllEmergenciesByNameContainingIgnoreCaseOrderByDateDesc() {
        Emergency emergency1 = new Emergency(
                "Extreme fire",
                new EmergencyLocation("Ukraine", "Kharkiv"),
                LocalDateTime.now(),
                Type.Fire
        );
        underTest.save(emergency1);
        Emergency emergency2 = new Emergency(
                "Troubles in Lviv",
                new EmergencyLocation("Ukraine", "Lviv"),
                LocalDateTime.now().minusDays(2),
                Type.Earthquake
        );
        underTest.save(emergency2);

        List<Emergency> expected = new ArrayList<Emergency>();
        expected.add(emergency1);
        expected.add(emergency2);

        assertEquals(expected, underTest.findAllByNameContainingIgnoreCaseOrderByDateDesc("tr"));
    }
}
