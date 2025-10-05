package om.healthmate.javaproject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.entity.Question;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.QuestionRepository;
import om.healthmate.javaproject.repository.UserRepository;



@Service

public class QuestionService {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private UserRepository userRepo;

    public Question askQuestion(Question q) { return questionRepo.save(q); }
    public List<Question> getAllQuestions() { return questionRepo.findAllByOrderByCreatedAtDesc(); }

    public Question findById(Long id) { return questionRepo.findById(id).orElse(null); }

    // Lấy các câu hỏi đã phân công cho bác sĩ
    public List<Question> getAssignedQuestionsForDoctor(User doctor) {
        if (doctor == null) return java.util.Collections.emptyList();
        return questionRepo.findByAssignedDoctorOrderByAssignedAtDesc(doctor);
    }

    // Lấy các câu hỏi chưa phân công cho bác sĩ trực (tất cả hoặc theo chuyên khoa)
    public List<Question> getPublicQuestionsForDoctor() {
        return questionRepo.findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseOrderByCreatedAtDesc();
    }

    public List<Question> getPublicQuestionsForDoctorBySpecialization(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            return getPublicQuestionsForDoctor();
        }
        return questionRepo.findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseAndSpecializationOrderByCreatedAtDesc(specialization);
    }

    // Phân công bác sĩ cho câu hỏi
    public void assignDoctor(Long questionId, Long doctorId) {
        Question q = questionRepo.findById(questionId).orElse(null);
        User doctor = userRepo.findById(doctorId).orElse(null);
        if (q != null && doctor != null) {
            q.setAssignedDoctor(doctor);
            q.setAssignedAt(LocalDateTime.now());
            questionRepo.save(q);
        }
    }
        // Lấy các câu hỏi public cho user, có filter chuyên khoa và tìm kiếm keyword (tiêu đề/nội dung)
    public List<Question> getPublicQuestionsForUser(String specialization, String keyword) {
        if ((specialization == null || specialization.isEmpty()) && (keyword == null || keyword.isEmpty())) {
            return questionRepo.findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseOrderByCreatedAtDesc();
        } else if (specialization != null && !specialization.isEmpty() && (keyword == null || keyword.isEmpty())) {
            return questionRepo.findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseAndSpecializationOrderByCreatedAtDesc(specialization);
        } else if ((specialization == null || specialization.isEmpty()) && keyword != null && !keyword.isEmpty()) {
            return questionRepo.findPublicQuestionsByKeyword(keyword);
        } else {
            return questionRepo.findPublicQuestionsBySpecializationAndKeyword(specialization, keyword);
        }
    }
    // Lấy tất cả câu hỏi của 1 user
    public List<Question> getQuestionsByUser(User user) {
        if (user == null) return java.util.Collections.emptyList();
        return questionRepo.findByUserOrderByCreatedAtDesc(user);
    }
}