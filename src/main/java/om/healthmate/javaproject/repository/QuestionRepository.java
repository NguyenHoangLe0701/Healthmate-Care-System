package om.healthmate.javaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import om.healthmate.javaproject.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOrderByCreatedAtDesc();
    // Lấy các câu hỏi đã phân công cho bác sĩ (theo assignedDoctor)
    List<Question> findByAssignedDoctorOrderByAssignedAtDesc(om.healthmate.javaproject.entity.User assignedDoctor);
    // Lấy các câu hỏi chưa phân công cho bác sĩ trực, có thể lọc theo chuyên khoa
    List<Question> findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseOrderByCreatedAtDesc();
    List<Question> findByAssignedDoctorIsNullAndAnsweredAtIsNullAndClosedFalseAndRejectedFalseAndSpecializationOrderByCreatedAtDesc(String specialization);

    // Tìm kiếm câu hỏi public theo keyword (tiêu đề hoặc nội dung)
    @Query("SELECT q FROM Question q WHERE q.assignedDoctor IS NULL AND q.answeredAt IS NULL AND q.closed = false AND q.rejected = false AND (LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) ORDER BY q.createdAt DESC")
    List<Question> findPublicQuestionsByKeyword(@Param("keyword") String keyword);

    // Tìm kiếm câu hỏi public theo chuyên khoa và keyword
    @Query("SELECT q FROM Question q WHERE q.assignedDoctor IS NULL AND q.answeredAt IS NULL AND q.closed = false AND q.rejected = false AND q.specialization = :specialization AND (LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) ORDER BY q.createdAt DESC")
    List<Question> findPublicQuestionsBySpecializationAndKeyword(@Param("specialization") String specialization, @Param("keyword") String keyword);
        // Lấy tất cả câu hỏi của 1 user
    List<Question> findByUserOrderByCreatedAtDesc(om.healthmate.javaproject.entity.User user);
}