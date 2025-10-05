package om.healthmate.javaproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import om.healthmate.javaproject.entity.Appointment;
import om.healthmate.javaproject.repository.AppointmentRepository;
import om.healthmate.javaproject.service.AppointmentService;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointments")
    public String createAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.save(appointment);
        return "redirect:/appointment-booking?success";
    }

    @GetMapping("/dashboard/appointments")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.findAll();
        model.addAttribute("appointments", appointments);
        return "dashboard/appointment-layout";
    }

    @GetMapping("/appointment-booking")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new om.healthmate.javaproject.entity.Appointment());
        return "pages/appointment-booking";
    }
} 