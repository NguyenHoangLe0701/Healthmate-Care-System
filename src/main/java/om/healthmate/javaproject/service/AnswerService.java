package om.healthmate.javaproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.entity.Answer;
import om.healthmate.javaproject.repository.AnswerRepository;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepo;

    public Answer answerQuestion(Answer a) { return answerRepo.save(a); }
    public List<Answer> getAnswers(Long questionId) { return answerRepo.findByQuestionId(questionId); }

    public Answer getAnswerById(Long id) {
        return answerRepo.findById(id).orElse(null);
    }

    public Answer save(Answer answer) {
        return answerRepo.save(answer);
    }
}