package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    
}
