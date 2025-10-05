package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import om.healthmate.javaproject.service.HealthRecordService;
import om.healthmate.javaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/dashboard/services")
public class ServiceController {
    @Autowired
    private HealthRecordService healthRecordService;
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String servicePage(Model model) {
        model.addAttribute("title", "Quản lý dịch vụ");
        model.addAttribute("healthRecords", healthRecordService.findAll());
        model.addAttribute("users", userService.findAll());
        return "dashboard/service-layout";
    }

    @GetMapping("/health-records")
    public String healthRecords(Model model) {
        model.addAttribute("healthRecords", healthRecordService.findAll());
        model.addAttribute("users", userService.findAll());
        return "dashboard/service-management";
    }

    // Có thể mở rộng thêm các route cho thêm/sửa/xóa dịch vụ
} 