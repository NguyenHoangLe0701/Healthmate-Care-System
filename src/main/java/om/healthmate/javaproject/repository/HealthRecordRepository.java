package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.HealthRecord;
import om.healthmate.javaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByUser(User user);
    List<HealthRecord> findByUserOrderByIdDesc(User user);
    
    @Query("SELECT hr FROM HealthRecord hr LEFT JOIN FETCH hr.user")
    List<HealthRecord> findAllWithUser();
} 