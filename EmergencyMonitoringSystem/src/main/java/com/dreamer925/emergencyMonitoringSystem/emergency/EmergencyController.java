package com.dreamer925.emergencyMonitoringSystem.emergency;

import com.dreamer925.emergencyMonitoringSystem.user.Role;
import com.dreamer925.emergencyMonitoringSystem.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/emergencies")
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final UserRepository userRepository;

    @GetMapping("/tracker")
    public List<Emergency> getEmergencies(){
         return emergencyService.getAllEmergencies();
    }

    @GetMapping
    public List<Emergency> getUserEmergencies(Principal principal){
        if(userRepository.findByEmail(principal.getName()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with email " + principal.getName() +
                            " was not found.");
        }
        List<Emergency> emergencies =userRepository.findByEmail(principal.getName()).get().getEmergencies();
        if (emergencies.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return emergencies;
    }

    @GetMapping("/filter")
    public List<Emergency> getEmergency(@Size(min = 1, max = 1) @RequestParam Map<String, String> query) {
        System.out.println("filter");
        System.out.println(query);
        if (query.containsKey("location")) {
            return emergencyService.findEmergencyByLocation(query.get("location"));
        } else if (query.containsKey("date")) {
            return emergencyService.findEmergencyByDate(LocalDateTime.parse(query.get("date")));
        } else if (query.containsKey("type")) {
            return emergencyService.findEmergencyByType(Type.valueOf(query.get("type")));
        } else if (query.containsKey("name")) {
            return emergencyService.findEmergencyByName(query.get("name"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no such parameter for emergency.");
        }
    }

    @PostMapping
    public ResponseEntity<String> addEmergency(@Valid @RequestBody EmergencyRequest emergencyRequest,
                                               Principal principal, BindingResult bindingResult) {
        System.out.println(emergencyRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors().toString());
        }
//        Emergency emergency = new Emergency(emergencyRequest.name(), new EmergencyLocation(
//                emergencyRequest.country().isEmpty() ? "" : emergencyRequest.country(),
//                emergencyRequest.city().isEmpty() ? "" : emergencyRequest.city()),
//                emergencyRequest.date(), emergencyRequest.type());

        if(userRepository.findByEmail(principal.getName()).isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Emergency can be added only by authorized user. User with email " + principal.getName() +
                            " was not found.");
        }
        if (emergencyService.saveEmergency(emergencyRequest, userRepository.findByEmail(principal.getName()).get())) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Emergency has been added successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Emergency already exists.");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateEmergency(@PathVariable Integer id, @Valid @RequestBody EmergencyRequest emergencyRequest,
                                                  Principal principal) {
        if (emergencyService.findEmergencyById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Emergency with id = " + id + " does not exist.");
        }
        if(userRepository.findByEmail(principal.getName()).isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Emergency can be updated only by authorized user. User with email " +
                            principal.getName() + " was not found.");
        }
        if(userRepository.findByEmail(principal.getName()).get().getRole().equals(Role.USER) &&
                !userRepository.findByEmail(principal.getName()).get()
                        .getEmergencies().contains(emergencyService.findEmergencyById(id).get())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Emergency can be updated only by its author.");
        }
        emergencyService.updateEmergency(id, emergencyRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Emergency with id = " + id + "has been updated.");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmergency(@PathVariable Integer id, Principal principal) {
        if (emergencyService.findEmergencyById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Emergency with id = " + id + " does not exist.");
        }
        if(userRepository.findByEmail(principal.getName()).isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED    )
                    .body("Emergency can be deleted only by authorized user. User with email " +
                            principal.getName() + " was not found.");
        }
        if(userRepository.findByEmail(principal.getName()).get().getRole().equals(Role.USER) &&
                !userRepository.findByEmail(principal.getName()).get()
                        .getEmergencies().contains(emergencyService.findEmergencyById(id).get())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Emergency can be deleted only by its author.");
        }
        emergencyService.deleteEmergencyById(id, userRepository.findByEmail(principal.getName()).get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Emergency with id = " + id + "has been deleted.");
    }
}

