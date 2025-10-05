package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.AppointmentDv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDvRepository extends JpaRepository<AppointmentDv, Long> {
    // Thêm hàm custom nếu cần
} 