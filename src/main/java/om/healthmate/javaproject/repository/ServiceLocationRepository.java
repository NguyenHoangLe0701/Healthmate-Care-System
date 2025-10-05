package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {
} 