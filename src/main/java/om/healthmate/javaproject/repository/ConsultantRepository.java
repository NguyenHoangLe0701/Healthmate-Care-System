package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
    // Có thể thêm các method tùy chỉnh nếu cần
} 