package com.dreamer925.emergencyMonitoringSystem.emergency;

import com.dreamer925.emergencyMonitoringSystem.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmergencyService {
    private final EmergencyRepository emergencyRepository;

    public Optional<Emergency> findEmergencyById(Integer id){
        return emergencyRepository.findById(id);
    }

    //TODO: implement limit/pagination
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    public List<Emergency> findEmergencyByLocation(String location){
        return emergencyRepository.findByLocation(location);
    }

    public List<Emergency> findEmergencyByDate(LocalDateTime date){
        return emergencyRepository.findByDate(date);
    }

    public List<Emergency> findEmergencyByType(Type type){
        return emergencyRepository.findByType(type);
    }

    public List<Emergency> findEmergencyByName(String name){
        return emergencyRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Transactional
    public boolean saveEmergency(EmergencyRequest emergencyRequest, User user){
        if (emergencyRepository
                .existsByNameAndDateAndType(emergencyRequest.name(), emergencyRequest.date(),
                        emergencyRequest.type())) {
            return false;
        }

        Emergency emergency = new Emergency(emergencyRequest.name(), emergencyRequest.country(),
                emergencyRequest.city(), emergencyRequest.date(), emergencyRequest.type());

        this.emergencyRepository.save(emergency);

        if(!emergencyRepository.existsById(emergency.getId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Emergency with id " + emergency.getId() + " was not added.");
        }

        user.addEmergency(emergency);

        return true;
    }

    @Transactional
    public void updateEmergency(Integer id,EmergencyRequest emergencyRequest) {
        Emergency toUpdate = emergencyRepository.getReferenceById(id);
        if(!emergencyRequest.name().equals(toUpdate.getName())){
            toUpdate.setName(emergencyRequest.name());
        }
        if(!emergencyRequest.country().isBlank() && !emergencyRequest.country().equals(toUpdate.getLocation().getCountry())){
            toUpdate.setCountry(emergencyRequest.country());
        }
        if(!emergencyRequest.city().isBlank() && !emergencyRequest.city().equals(toUpdate.getLocation().getCity())){
            toUpdate.setCity(emergencyRequest.city());
        }
        if(!emergencyRequest.date().equals(toUpdate.getDate())){
            toUpdate.setDate(emergencyRequest.date());
        }
        if(!emergencyRequest.type().equals(toUpdate.getType())){
            toUpdate.setType(emergencyRequest.type());
        }
    }

    public void deleteEmergencyById(Integer id, User user){
        if(emergencyRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Emergency with id " + id + " was not found.");
        }
        user.deleteEmergency(emergencyRepository.findById(id).get());
        emergencyRepository.deleteById(id);
    }
}
