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
import org.springframework.web.bind.annotation.RequestParam;

import om.healthmate.javaproject.entity.Doctor;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.DoctorRepository;
import om.healthmate.javaproject.service.DoctorService;
import om.healthmate.javaproject.service.UserService;

@Controller
@RequestMapping("/dashboard/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    // Trang hiển thị danh sách bác sĩ
    @GetMapping("")
    public String doctorPage(Model model) {
        List<Doctor> doctorList = doctorRepository.findAll();
        model.addAttribute("title", "Quản lý bác sĩ");
        model.addAttribute("doctors", doctorList);
        return "dashboard/doctor-layout";
    }

    // Hiển thị form thêm bác sĩ
    @GetMapping("/add")
    public String addDoctorForm(Model model) {
        model.addAttribute("title", "Thêm bác sĩ mới");
        model.addAttribute("formMode", "add");
        model.addAttribute("doctor", new Doctor());
        return "dashboard/doctor_form";
    }

    // Lưu bác sĩ (thêm hoặc chỉnh sửa)
    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor,
                             @RequestParam(required = false) String password) {

        doctorRepository.save(doctor);

        // Nếu là thêm mới và chưa có user, thì tạo tài khoản cho bác sĩ
        if (doctor.getId() == null || userService.findByEmail(doctor.getEmail()) == null) {
            if (password != null && !password.isEmpty()) {
                User user = new User();
                user.setName(doctor.getName());
                user.setEmail(doctor.getEmail());
                user.setPassword(password);
                user.setRole("DOCTOR");
                user.setSpecialization(doctor.getSpecialty());
                userService.register(user);
            }
        }
        return "redirect:/dashboard/doctors";
    }

    // Form chỉnh sửa bác sĩ
    @GetMapping("/edit/{id}")
    public String editDoctorForm(@PathVariable Long id, Model model) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ"));
        model.addAttribute("title", "Chỉnh sửa bác sĩ");
        model.addAttribute("formMode", "edit");
        model.addAttribute("doctor", doctor);
        return "dashboard/doctor_form";
    }

    // Xóa bác sĩ
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/dashboard/doctors";
    }
}
