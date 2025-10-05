package om.healthmate.javaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/settings")
public class SettingController {

    @GetMapping("")
    public String settingPage(Model model) {
        model.addAttribute("title", "Cài đặt hệ thống");
        return "dashboard/setting-layout";
    }

    // Có thể mở rộng thêm các route cho đổi mật khẩu, thông tin tài khoản, ...
} 