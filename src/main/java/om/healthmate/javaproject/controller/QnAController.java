package om.healthmate.javaproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import om.healthmate.javaproject.entity.Answer;
import om.healthmate.javaproject.entity.Question;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.UserRepository;
import om.healthmate.javaproject.service.AnswerService;
import om.healthmate.javaproject.service.QuestionService;

@Controller
@RequestMapping("/cong-dong")
public class QnAController {
    // Xử lý cảm ơn câu hỏi
    @Autowired
    private om.healthmate.javaproject.repository.ThankHistoryRepository thankHistoryRepo;

    @PostMapping("/thank")
    public String thankQuestion(@RequestParam Long questionId, HttpSession session) {
        Question question = questionService.findById(questionId);
        if (question == null) {
            return "redirect:/cong-dong";
        }
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/cong-dong/question/" + questionId;
        }
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return "redirect:/cong-dong/question/" + questionId;
        }
        boolean alreadyThanked = thankHistoryRepo.findByQuestionAndUser(question, user).isPresent();
        if (!alreadyThanked) {
            // Lưu lịch sử cảm ơn
            om.healthmate.javaproject.entity.ThankHistory thank = new om.healthmate.javaproject.entity.ThankHistory(question, user);
            thankHistoryRepo.save(thank);
            // Tăng thankCount
            Integer thankCount = question.getThankCount();
            int current = (thankCount != null) ? thankCount : 0;
            question.setThankCount(current + 1);
            questionService.askQuestion(question);
        }
        return "redirect:/cong-dong/question/" + questionId;
    }
    @Autowired private QuestionService questionService;
    @Autowired private AnswerService answerService;
    @Autowired private UserRepository userRepo;

    @GetMapping
    public String listQuestions(Model model, HttpSession session) {
        model.addAttribute("questions", questionService.getAllQuestions());
        // Lấy danh sách bác sĩ để phân công (role = DOCTOR)
        List<User> doctors = userRepo.findByRole("DOCTOR");
        model.addAttribute("doctors", doctors);

        // Lấy userId từ session nếu có
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            User currentUser = userRepo.findByEmail(email);
            if (currentUser != null) {
                model.addAttribute("userId", currentUser.getId());
                // Nếu là doctor thì truyền danh sách assignedQuestions và publicQuestions
                if ("DOCTOR".equals(currentUser.getRole())) {
                    model.addAttribute("assignedQuestions", questionService.getAssignedQuestionsForDoctor(currentUser));
                    model.addAttribute("publicQuestions", questionService.getPublicQuestionsForDoctor());
                }
            }
        }
        return "hoidap/cong-dong";
    }
    // Phân công bác sĩ trả lời câu hỏi (ADMIN)
    @PostMapping("/assign")
    public String assignDoctor(@RequestParam Long questionId, @RequestParam Long doctorId) {
        questionService.assignDoctor(questionId, doctorId);
        return "redirect:/cong-dong";
    }

    @GetMapping("/question/{id}")
    public String questionDetail(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        List<Answer> answers = answerService.getAnswers(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        // Kiểm tra user đã cảm ơn chưa (dùng session)
        boolean userThanked = false;
        String email = null;
        org.springframework.web.context.request.RequestAttributes requestAttributes = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            Object emailObj = requestAttributes.getAttribute("userEmail", org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION);
            if (emailObj instanceof String emailStr) {
                email = emailStr;
            }
        }
        if (email != null) {
            User user = userRepo.findByEmail(email);
            if (user != null) {
                userThanked = thankHistoryRepo.findByQuestionAndUser(question, user).isPresent();
            }
        }
        model.addAttribute("userThanked", userThanked);
        return "hoidap/question-detail";
    }

    @PostMapping("/ask")
    public String askQuestion(@ModelAttribute Question question, @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        User user = userRepo.findByEmail(email);
        question.setUser(user);
        question.setCreatedAt(LocalDateTime.now());
        // Xử lý upload ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Lưu vào target/classes/static/images/hoidap để Spring Boot phục vụ file tĩnh
                String uploadsDir = new java.io.File("target/classes/static/images/hoidap/").getAbsolutePath() + java.io.File.separator;
                java.io.File dir = new java.io.File(uploadsDir);
                if (!dir.exists()) dir.mkdirs();
                String originalName = java.util.UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                String filePath = uploadsDir + originalName;
                imageFile.transferTo(new java.io.File(filePath));
                // Lưu đường dẫn để truy cập qua web
                question.setImageUrl("/images/hoidap/" + originalName);
            } catch (java.io.IOException | IllegalStateException e) {
                org.slf4j.LoggerFactory.getLogger(QnAController.class).error("Error uploading image", e);
            }
        }
        questionService.askQuestion(question);
        return "redirect:/cong-dong";
    }

    @PostMapping("/answer")
    public String answerQuestion(@RequestParam Long questionId, @RequestParam String content, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        User doctor = userRepo.findByEmail(email);
        Question question = questionService.findById(questionId);
        if (doctor != null && question != null && "DOCTOR".equals(doctor.getRole()) && (question.getClosed() == null || !question.getClosed()) && (question.getRejected() == null || !question.getRejected())) {
            Answer answer = new Answer();
            answer.setContent(content);
            answer.setCreatedAt(LocalDateTime.now());
            answer.setDoctor(doctor);
            answer.setQuestion(question);
            answerService.answerQuestion(answer);
            // Đánh dấu đã trả lời
            question.setAnsweredAt(LocalDateTime.now());
            questionService.askQuestion(question);
        }
        return "redirect:/cong-dong/question/" + questionId;
    }

    // Bác sĩ từ chối câu hỏi
    @PostMapping("/reject")
    public String rejectQuestion(@RequestParam Long questionId, @RequestParam(required = false) String reason, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        User doctor = userRepo.findByEmail(email);
        Question question = questionService.findById(questionId);
        if (doctor != null && question != null && "DOCTOR".equals(doctor.getRole()) && (question.getClosed() == null || !question.getClosed()) && (question.getRejected() == null || !question.getRejected())) {
            question.setRejected(true);
            question.setClosed(true); // Đóng luôn câu hỏi khi bị từ chối
            question.setRejectedReason(reason);
            questionService.askQuestion(question);
        }
        return "redirect:/cong-dong/question/" + questionId;
    }
    
    // User reply to doctor answer
    @PostMapping("/reply")
    public String replyToDoctor(@RequestParam Long answerId, @RequestParam String replyContent, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            // Not logged in
            return "redirect:/cong-dong";
        }
        User user = userRepo.findByEmail(email);
        if (user == null || !"USER".equals(user.getRole())) {
            // Only USER can reply
            return "redirect:/cong-dong";
        }
        Answer answer = answerService.getAnswerById(answerId);
        if (answer == null) {
            return "redirect:/cong-dong";
        }
        Question question = answer.getQuestion();
        // Only allow reply if not already replied
        if (answer.getUserReply() != null) {
            return "redirect:/cong-dong/question/" + (question != null ? question.getId() : "");
        }
        // Only allow reply if question is not closed or rejected
        if (question == null || (question.getClosed() != null && question.getClosed()) || (question.getRejected() != null && question.getRejected())) {
            return "redirect:/cong-dong/question/" + (question != null ? question.getId() : "");
        }
        // Only allow reply if this user is the owner of the question
        if (question.getUser() == null || !user.getId().equals(question.getUser().getId())) {
            // Not the owner
            return "redirect:/cong-dong/question/" + question.getId();
        }
        answer.setUserReply(replyContent);
        answer.setUserReplyAt(java.time.LocalDateTime.now());
        answerService.save(answer);
        return "redirect:/cong-dong/question/" + question.getId();
    }
    @GetMapping("/cau-hoi-cua-toi")
    public String myQuestions(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/cong-dong";
        }
        User user = userRepo.findByEmail(email);
        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/cong-dong";
        }
        // Lấy tất cả câu hỏi của user này
        List<Question> questions = questionService.getQuestionsByUser(user);
        model.addAttribute("questions", questions);
        return "hoidap/cau-hoi-cua-toi";
    }
}