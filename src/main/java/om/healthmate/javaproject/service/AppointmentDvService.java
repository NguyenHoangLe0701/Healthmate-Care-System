package om.healthmate.javaproject.service;

import om.healthmate.javaproject.entity.AppointmentDv;
import om.healthmate.javaproject.repository.AppointmentDvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentDvService {
    @Autowired
    private AppointmentDvRepository appointmentDvRepository;

    public AppointmentDv save(AppointmentDv appointment) {
        return appointmentDvRepository.save(appointment);
    }
} 