package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
} 