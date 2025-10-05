package om.healthmate.javaproject.repository;

import om.healthmate.javaproject.entity.HealthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HealthServiceRepository extends JpaRepository<HealthService, Long> {
    List<HealthService> findByCategory_Id(Long categoryId);
    Page<HealthService> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<HealthService> findAll(Pageable pageable);

    @Query("SELECT h FROM HealthService h WHERE (:categoryId IS NULL OR h.category.id = :categoryId) " +
           "AND (:locationId IS NULL OR h.location.id = :locationId) " +
           "AND (:keyword IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<HealthService> search(@Param("categoryId") Long categoryId,
                               @Param("locationId") Long locationId,
                               @Param("keyword") String keyword,
                               Pageable pageable);
} 