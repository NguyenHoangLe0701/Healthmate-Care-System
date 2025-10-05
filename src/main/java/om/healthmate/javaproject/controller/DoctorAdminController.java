package om.healthmate.javaproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.UserRepository;

@Controller
@RequestMapping("/admin/doctors")
public class DoctorAdminController {
    @Autowired
    private UserRepository userRepository;

    // Hiển thị danh sách bác sĩ
    @GetMapping("")
    public String listDoctors(Model model) {
        List<User> doctors = userRepository.findByRole("DOCTOR");
        model.addAttribute("doctors", doctors);
        model.addAttribute("specializations", User.SPECIALIZATIONS);
        return "admin/doctor-list";
    }

    // Hiển thị form thêm bác sĩ
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new User());
        model.addAttribute("specializations", User.SPECIALIZATIONS);
        return "admin/doctor-form";
    }

    // Xử lý thêm bác sĩ
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctor") User doctor) {
        doctor.setRole("DOCTOR");
        userRepository.save(doctor);
        return "redirect:/admin/doctors";
    }

    // Hiển thị form sửa bác sĩ
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User doctor = userRepository.findById(id).orElseThrow();
        model.addAttribute("doctor", doctor);
        model.addAttribute("specializations", User.SPECIALIZATIONS);
        return "admin/doctor-form";
    }

    // Xử lý sửa bác sĩ
    @PostMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, @ModelAttribute("doctor") User doctor) {
        doctor.setId(id);
        doctor.setRole("DOCTOR");
        userRepository.save(doctor);
        return "redirect:/admin/doctors";
    }

    // Xóa bác sĩ
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/doctors";
    }
}
