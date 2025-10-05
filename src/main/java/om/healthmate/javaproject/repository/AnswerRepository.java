package om.healthmate.javaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import om.healthmate.javaproject.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}