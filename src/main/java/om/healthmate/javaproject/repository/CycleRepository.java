package om.healthmate.javaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import om.healthmate.javaproject.entity.Cycle;
import om.healthmate.javaproject.entity.User;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {
    List<Cycle> findByUser(User user);
}