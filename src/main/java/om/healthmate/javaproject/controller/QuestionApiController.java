package om.healthmate.javaproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import om.healthmate.javaproject.entity.Question;
import om.healthmate.javaproject.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionApiController {
    @Autowired
    private QuestionService questionService;

    // API lấy danh sách câu hỏi public theo chuyên khoa và keyword (AJAX cho user/doctor)
    @GetMapping("/public")
    public List<Question> getPublicQuestions(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String keyword) {
        return questionService.getPublicQuestionsForUser(specialization, keyword);
    }
}
