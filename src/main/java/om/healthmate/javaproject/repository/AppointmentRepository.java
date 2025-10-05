package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate; 
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndAppointmentDate(String doctorName, LocalDate appointmentDate);
}