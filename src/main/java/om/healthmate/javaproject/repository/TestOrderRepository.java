package om.healthmate.javaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import om.healthmate.javaproject.entity.TestOrder;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrder, Long> {
    List<TestOrder> findByUserId(Long userId);
}
