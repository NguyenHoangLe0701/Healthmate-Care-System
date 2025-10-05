package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.AppointmentDv;
import om.healthmate.javaproject.service.HealthServiceService;
import om.healthmate.javaproject.entity.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/appointment-dv")
public class AppointmentDvController {
    // @Autowired
    // private AppointmentDvService appointmentDvService;
    @Autowired
    private HealthServiceService healthServiceService;

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute AppointmentDv appointment, RedirectAttributes redirectAttributes) {
        // Không lưu vào DB ở bước này nữa
        // appointment.setStatus("pending");
        // appointmentDvService.save(appointment);
        // redirectAttributes.addFlashAttribute("message", "Đặt lịch thành công!");
        return "redirect:/dichvu/thanh-toan?serviceId=" + appointment.getServiceId()
            + "&appointmentDate=" + appointment.getAppointmentDate()
            + "&appointmentTime=" + appointment.getAppointmentTime();
    }

    @GetMapping("/confirm")
    public String confirmBooking(@RequestParam Long serviceId,
                                 @RequestParam String appointmentDate,
                                 @RequestParam String appointmentTime,
                                 Model model) {
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("appointmentDate", appointmentDate);
        model.addAttribute("appointmentTime", appointmentTime);
        // Lấy thông tin dịch vụ từ DB
        HealthService service = healthServiceService.getById(serviceId);
        if (service != null) {
            model.addAttribute("serviceImageUrl", service.getImageUrl());
            model.addAttribute("serviceName", service.getName());
            model.addAttribute("serviceLocation", service.getLocation() != null ? service.getLocation().getAddress() : "");
            model.addAttribute("servicePrice", service.getPrice());
        }
        return "dichvu/xac-nhan-dat-kham";
    }
} 