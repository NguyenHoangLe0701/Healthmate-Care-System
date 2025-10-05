package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.Consultant;
import om.healthmate.javaproject.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/consultants")
public class ConsultantController {
    
    @Autowired
    private ConsultantService consultantService;
    
    @GetMapping("")
    public String consultantPage(Model model) {
        model.addAttribute("consultants", consultantService.findAll());
        return "dashboard/consultant-layout";
    }
    
    @PostMapping("/add")
    public String addConsultant(@ModelAttribute Consultant consultant) {
        if (consultant.getStatus() == null || consultant.getStatus().isEmpty()) {
            consultant.setStatus("ACTIVE");
        }
        consultantService.save(consultant);
        return "redirect:/dashboard/consultants";
    }
    
    @PostMapping("/edit")
    public String editConsultant(@ModelAttribute Consultant consultant) {
        if (consultant.getStatus() == null || consultant.getStatus().isEmpty()) {
            consultant.setStatus("ACTIVE");
        }
        consultantService.save(consultant);
        return "redirect:/dashboard/consultants";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteConsultant(@PathVariable Long id) {
        consultantService.deleteById(id);
        return "redirect:/dashboard/consultants";
    }
} 