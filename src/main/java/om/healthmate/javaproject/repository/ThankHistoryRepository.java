package om.healthmate.javaproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import om.healthmate.javaproject.entity.Question;
import om.healthmate.javaproject.entity.ThankHistory;
import om.healthmate.javaproject.entity.User;

@Repository
public interface ThankHistoryRepository extends JpaRepository<ThankHistory, Long> {
    Optional<ThankHistory> findByQuestionAndUser(Question question, User user);
    long countByQuestion(Question question);
}
