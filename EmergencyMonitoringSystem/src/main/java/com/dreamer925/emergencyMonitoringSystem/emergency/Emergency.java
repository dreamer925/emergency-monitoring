package com.dreamer925.emergencyMonitoringSystem.emergency;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emergency {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank
    private String name;
    @OneToOne
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private EmergencyLocation location;
    @NotNull
    private LocalDateTime date;
    @NotNull
    @Enumerated
    private Type type;

    public Emergency(String name, EmergencyLocation location, LocalDateTime date, Type type) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.type = type;
    }

    public Emergency(String name, String country, String city, LocalDateTime date, Type type) {
        this.name = name;
        this.location = new EmergencyLocation(country, city);
        this.date = date;
        this.type = type;
    }

    public void setCountry(String country) {
        this.location.setCountry(country);
    }

    public void setCity(String city) {
        this.location.setCity(city);
    }
}

