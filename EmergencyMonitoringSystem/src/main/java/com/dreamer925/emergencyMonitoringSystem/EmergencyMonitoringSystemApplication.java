package com.dreamer925.emergencyMonitoringSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmergencyMonitoringSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyMonitoringSystemApplication.class, args);
    }
/*
    @Bean
    CommandLineRunner addEm(EmergencyRepository emergencyRepository) {
        return args -> {
            emergencyRepository.save(new Emergency("tornado", new EmergencyLocation("USA","Kentucky"), LocalDateTime.now(), Type.Tornadoes));
            emergencyRepository.save(new Emergency("Extreme fire", new EmergencyLocation("Ukraine","Kharkiv"), LocalDateTime.now(), Type.Fire));
            emergencyRepository.save(new Emergency("Covid-19. New deaths", new EmergencyLocation("USA","Northern California"), LocalDateTime.now(), Type.Pandemics));
            emergencyRepository.save(new Emergency("Hurricane", new EmergencyLocation("","Chili"), LocalDateTime.now(), Type.Hurricanes));
        };
    }

    @Bean
    CommandLineRunner addUs(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return args -> {
            User user = new User("Mary", "em@mail.com", passwordEncoder.encode("password"), Role.USER);
            user.setLocked(false);
            userRepository.save(user);
            User admin = new User("Dan", "sup@mail.com", passwordEncoder.encode("password"), Role.ADMIN);
            admin.setLocked(false);
            userRepository.save(admin);
        };
    }
*/
}
