package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.Appointment;
import om.healthmate.javaproject.entity.Doctor;
import om.healthmate.javaproject.repository.DoctorRepository;
import om.healthmate.javaproject.repository.AppointmentRepository;
import om.healthmate.javaproject.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; 
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class PublicDoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/bac-si-noi-bat")
    public String showDoctorsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String appointmentType,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate,
            Model model) {

        int pageSize = 8;
        PageRequest pageable = PageRequest.of(page, pageSize);

        List<Doctor> allDoctors = doctorRepository.findAll();

        List<Doctor> filteredDoctors = allDoctors.stream()
            .filter(doctor -> {
                boolean matchesQuery = (query == null || query.isEmpty()) ||
                                       doctor.getName().toLowerCase().contains(query.toLowerCase()) ||
                                       (doctor.getSpecialty() != null && doctor.getSpecialty().toLowerCase().contains(query.toLowerCase()));

                boolean matchesLocation = (location == null || location.isEmpty() || "all".equals(location)) ||
                                          (doctor.getLocation() != null && doctor.getLocation().equalsIgnoreCase(location));

                boolean matchesApptType = (appointmentType == null || appointmentType.isEmpty() || "all".equals(appointmentType)) ||
                                          (doctor.getAppointmentType() != null && doctor.getAppointmentType().equalsIgnoreCase(appointmentType));

                boolean matchesDateAvailability = true;

                return matchesQuery && matchesLocation && matchesApptType && matchesDateAvailability; 
            })
            .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredDoctors.size());
        List<Doctor> pageContent = new ArrayList<>();
        if (start <= end) {
             pageContent = filteredDoctors.subList(start, end);
        }

        Page<Doctor> doctorPage = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, filteredDoctors.size());


        model.addAttribute("doctors", doctorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", doctorPage.getTotalPages());

        model.addAttribute("query", query);
        model.addAttribute("location", location);
        model.addAttribute("appointmentType", appointmentType);
        model.addAttribute("appointmentDate", appointmentDate);

        return "pages/doctors/doctorsListing";
    }

    @GetMapping("/ho-so-bac-si/{id}")
    public String showDoctorProfile(@PathVariable("id") Long id, Model model, HttpSession session) {
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            return "error/404";
        }

        LocalDate today = LocalDate.now();

        int targetYear = today.getYear();
        int targetMonth = today.getMonthValue();

        LocalDate firstDayOfMonth = LocalDate.of(targetYear, targetMonth, 1);
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

        List<String> allTimeSlots = Arrays.asList(
            "08:00","08:15","08:30","08:45","09:00","09:15",
            "09:30","09:45","10:00","10:15","10:30","10:45",
            "11:00","11:15","11:30","11:45",
            "13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45",
            "15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45",
            "20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45"
        );

        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor.getName(), LocalDate.now());
        List<String> bookedTimeSlots = new ArrayList<>();
        for (Appointment appt : bookedAppointments) {
            bookedTimeSlots.add(appt.getAppointmentTime());
        }

        Boolean showLoginOverlay = (Boolean) model.getAttribute("showLoginOverlay");
        if (showLoginOverlay != null && showLoginOverlay) {
            model.addAttribute("showLoginOverlay", true);
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("today", today);
        model.addAttribute("allTimeSlots", allTimeSlots);
        model.addAttribute("bookedTimeSlots", bookedTimeSlots); 
        model.addAttribute("currentMonth", targetMonth);
        model.addAttribute("currentYear", targetYear);
        model.addAttribute("firstDayOfWeekValue", firstDayOfWeek.getValue());

        return "pages/doctors/doctorDetail";
    }

    @GetMapping("/api/doctor/{id}/available-slots")
    @ResponseBody
    public Map<String, Object> getAvailableSlots(
            @PathVariable("id") Long doctorId,
            @RequestParam("date") String dateStr) {

        LocalDate selectedDate;
        try {
            selectedDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            return Map.of("error", "Invalid date format");
        }

        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return Map.of("error", "Doctor not found");
        }

        List<String> allTimeSlots = Arrays.asList(
                "08:00","08:15","08:30","08:45","09:00","09:15",
                "09:30","09:45","10:00","10:15","10:30","10:45",
                "11:00","11:15","11:30","11:45",
                "13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45",
                "15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45",
                "20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45"
        );

        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor.getName(), selectedDate);
        List<String> bookedTimeSlots = new ArrayList<>();
        for (Appointment appt : bookedAppointments) {
            bookedTimeSlots.add(appt.getAppointmentTime());
        }

        List<String> availableTimeSlots = new ArrayList<>();
        LocalTime currentTime = LocalTime.now(); 

        LocalDate currentDate = LocalDate.now();

        for (String slot : allTimeSlots) {
            LocalTime slotTime = LocalTime.parse(slot);

            boolean isPastTime = false;
            if (selectedDate.isEqual(currentDate) && slotTime.isBefore(currentTime)) {
                isPastTime = true;
            }

            boolean isBooked = bookedTimeSlots.contains(slot);

            if (!isPastTime && !isBooked) {
                availableTimeSlots.add(slot);
            }
        }
        return Map.of("timeSlots", availableTimeSlots);
    }


    @GetMapping("/thong-tin-dat-kham")
    public String showBookingInfoGet(
        @RequestParam("doctorId") Long doctorId,
        @RequestParam("selectedTime") String selectedTime,
        @RequestParam("selectedDate") String selectedDateStr, 
        Model model,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        if (session.getAttribute("userName") == null) {
            redirectAttributes.addFlashAttribute("showLoginOverlay", true);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để tiếp tục đặt lịch.");
            return "redirect:/ho-so-bac-si/" + doctorId;
        }

        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bác sĩ.");
            return "redirect:/";
        }

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("error", "Ngày bạn chọn không hợp lệ. Vui lòng thử lại.");
            return "redirect:/ho-so-bac-si/" + doctorId;
        }

        session.setAttribute("bookingDoctorId", doctorId);
        session.setAttribute("bookingSelectedDate", localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        session.setAttribute("bookingSelectedTime", selectedTime);
        session.setAttribute("bookingDoctorName", doctor.getName());
        session.setAttribute("bookingDoctorSpecialty", doctor.getSpecialty());
        session.setAttribute("bookingDoctorPrice", doctor.getPrice());

        model.addAttribute("doctor", doctor);
        model.addAttribute("selectedTime", selectedTime);
        model.addAttribute("selectedDate", localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return "pages/doctors/bookingInfo";
    }

    @GetMapping("/thanh-toan")
    public String thanhToanPage(
        @RequestParam(required = false) String symptom,
        Model model,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        Long doctorId = (Long) session.getAttribute("bookingDoctorId");
        String selectedDate = (String) session.getAttribute("bookingSelectedDate");
        String selectedTime = (String) session.getAttribute("bookingSelectedTime");
        String doctorName = (String) session.getAttribute("bookingDoctorName");
        String doctorSpecialty = (String) session.getAttribute("bookingDoctorSpecialty");
        Double doctorPrice = (Double) session.getAttribute("bookingDoctorPrice");

        if (doctorId == null || selectedDate == null || selectedTime == null) {
            redirectAttributes.addFlashAttribute("error", "Thông tin đặt lịch không đầy đủ. Vui lòng thử lại.");
            return "redirect:/";
        }

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName(doctorName);
        doctor.setSpecialty(doctorSpecialty);
        doctor.setPrice(doctorPrice);

        model.addAttribute("doctor", doctor);
        model.addAttribute("selectedTime", selectedTime);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("symptom", symptom);

        if (redirectAttributes.getFlashAttributes().containsKey("bookingSuccess")) {
            model.addAttribute("bookingSuccess", true);
        }

        return "pages/doctors/paymentPage";
    }

    @PostMapping("/xac-nhan-thanh-toan")
    public String confirmPayment(
        @RequestParam Long doctorId,
        @RequestParam String selectedDate,
        @RequestParam String selectedTime,
        @RequestParam String symptom,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

        String patientName = (String) session.getAttribute("userName");
        String patientPhone = (String) session.getAttribute("userPhone");
        if (patientName == null) {
            redirectAttributes.addFlashAttribute("showLoginOverlay", true);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để hoàn tất đặt lịch.");
            return "redirect:/";
        }

        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bác sĩ.");
            return "redirect:/";
        }

        LocalDate appointmentLocalDate;
        try {
            appointmentLocalDate = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("error", "Ngày hẹn không hợp lệ. Vui lòng thử lại.");
            return "redirect:/";
        }

        Appointment appointment = new Appointment();
        appointment.setPatientName(patientName);
        appointment.setPatientPhone(patientPhone != null ? patientPhone : "Không có số điện thoại");
        appointment.setDoctor(doctor.getName());
        appointment.setAppointmentDate(appointmentLocalDate);
        appointment.setAppointmentTime(selectedTime);
        appointment.setSymptoms(symptom);
        appointment.setAppointmentType("Khám trực tuyến");

        appointmentRepository.save(appointment);

        session.removeAttribute("bookingDoctorId");
        session.removeAttribute("bookingSelectedDate");
        session.removeAttribute("bookingSelectedTime");
        session.removeAttribute("bookingDoctorName");
        session.removeAttribute("bookingDoctorSpecialty");
        session.removeAttribute("bookingDoctorPrice");

        redirectAttributes.addFlashAttribute("bookingSuccess", true);
        return "redirect:/";
    }

    @PostMapping("/thong-tin-dat-kham")
    public String showBookingInfoPost(
        @RequestParam("doctorId") Long doctorId,
        @RequestParam("selectedTime") String selectedTime,
        Model model
    ) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) return "error/404";

        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        model.addAttribute("doctor", doctor);
        model.addAttribute("selectedTime", selectedTime);
        model.addAttribute("today", today);

        return "pages/doctors/bookingInfo";
    }
}