package com.dreamer925.emergencyMonitoringSystem.emergency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmergencyLocation {
    @Id
    @GeneratedValue
    private int id;
    private String country;
    private String city;

    public EmergencyLocation(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
