package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/hospitals")
public class HospitalController {
    @GetMapping("")
    public String hospitalPage(Model model) {
        model.addAttribute("title", "Quản lý cơ sở y tế");
        return "dashboard/hospital-layout";
    }
    // Có thể mở rộng thêm các route cho thêm/sửa/xóa cơ sở y tế
} 