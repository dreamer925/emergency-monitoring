package com.dreamer925.emergencyMonitoringSystem.emergency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
    @Query("select e from Emergency e where e.location.country = ?1 or e.location.city = ?1")
    List<Emergency> findByLocation(String location);
    List<Emergency> findByDate(LocalDateTime date);
    List<Emergency> findByType(Type type);
    List<Emergency> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
    boolean existsByNameAndDateAndType(String name, LocalDateTime date, Type type);
}
