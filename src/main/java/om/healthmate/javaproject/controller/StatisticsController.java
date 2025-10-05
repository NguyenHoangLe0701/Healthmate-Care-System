package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import om.healthmate.javaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class StatisticsController {
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard/statistics")
    public String statisticsPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "dashboard/statistics-layout";
    }
} 