package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackController {
    @GetMapping("/dashboard/feedback")
    public String feedbackPage() {
        return "dashboard/feedback-layout";
    }
} 