package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/health-records")
public class HealthRecordController {
    
    @GetMapping("")
    public String healthRecordsPage(Model model, HttpSession session) {
        model.addAttribute("title", "Hồ Sơ Sức Khỏe");
        
        // Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("currentUserId", userId);
        } else {
            model.addAttribute("currentUserId", null);
        }
        return "pages/health-records";
    }
} 