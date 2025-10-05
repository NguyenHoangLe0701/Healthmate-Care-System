package om.healthmate.javaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import om.healthmate.javaproject.entity.Cycle;
import om.healthmate.javaproject.entity.PillReminder;

public interface PillReminderRepository extends JpaRepository<PillReminder, Long> {
    List<PillReminder> findByCycle(Cycle cycle);
}