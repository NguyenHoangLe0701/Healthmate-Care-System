package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping("")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "redirect:/";
        }
        model.addAttribute("title", "Dashboard Quản Lý");
        return "dashboard/dashboard-layout";
    }

    // @GetMapping("/doctors")
    // public String doctorManagement(Model model) {
    //     model.addAttribute("title", "Quản lý bác sĩ");
    //     model.addAttribute("content", "dashboard/doctor-management :: content");
    //     return "dashboard/dashboard-layout";
    // }

    // @GetMapping("/hospitals")
    // public String hospitalManagement(Model model) {
    //     model.addAttribute("title", "Quản lý cơ sở y tế");
    //     model.addAttribute("content", "dashboard/hospital-management :: content");
    //     return "dashboard/dashboard-layout";
    // }

    // @GetMapping("/services")
    // public String serviceManagement(Model model) {
    //     model.addAttribute("title", "Quản lý dịch vụ");
    //     model.addAttribute("content", "dashboard/service-management :: content");
    //     return "dashboard/dashboard-layout";
    // }

    // @GetMapping("/appointments")
    // public String appointmentManagement(Model model) {
    //     model.addAttribute("title", "Lịch hẹn");
    //     model.addAttribute("content", "pages/appointment-management :: content");
    //     return "dashboard/dashboard-layout";
    // }

} 