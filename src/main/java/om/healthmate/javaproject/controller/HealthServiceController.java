package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.service.HealthServiceService;
import om.healthmate.javaproject.service.ServiceCategoryService;
import om.healthmate.javaproject.entity.HealthService;
import om.healthmate.javaproject.entity.ServiceCategory;
import om.healthmate.javaproject.entity.ServiceLocation;
import om.healthmate.javaproject.service.ServiceLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dichvu")
public class HealthServiceController {
    @Autowired
    private HealthServiceService healthServiceService;
    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @Autowired
    private ServiceLocationService serviceLocationService;
    @Autowired
    private om.healthmate.javaproject.service.AppointmentDvService appointmentDvService;

    @GetMapping
    public String listHealthServices(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortPrice", required = false) String sortPrice,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
        int pageSize = 10;
        Sort sort = Sort.unsorted();
        if ("asc".equals(sortPrice)) {
            sort = Sort.by("price").ascending();
        } else if ("desc".equals(sortPrice)) {
            sort = Sort.by("price").descending();
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Page<HealthService> healthServices = healthServiceService.search(categoryId, locationId, keyword, pageable);
        List<ServiceCategory> categories = serviceCategoryService.getAllCategories();
        List<ServiceLocation> locations = serviceLocationService.getAllLocations();
        model.addAttribute("services", healthServices.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", healthServices.getTotalPages());
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedLocationId", locationId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortPrice", sortPrice);
        model.addAttribute("totalServices", healthServices.getTotalElements());
        return "pages/dichvu";
    }

    @GetMapping("/filter")
    public String filterHealthServices(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortPrice", required = false) String sortPrice,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
        int pageSize = 10;
        Sort sort = Sort.unsorted();
        if ("asc".equals(sortPrice)) {
            sort = Sort.by("price").ascending();
        } else if ("desc".equals(sortPrice)) {
            sort = Sort.by("price").descending();
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Page<HealthService> healthServices = healthServiceService.search(categoryId, locationId, keyword, pageable);

        model.addAttribute("services", healthServices.getContent());
        // Nếu fragment cần thêm các biến khác, truyền vào đây

        return "dichvu/service-list :: serviceList";
    }

    @GetMapping("/thanh-toan")
    public String paymentPage(@RequestParam Long serviceId,
                              @RequestParam String appointmentDate,
                              @RequestParam String appointmentTime,
                              Model model) {
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("appointmentDate", appointmentDate);
        model.addAttribute("appointmentTime", appointmentTime);
        return "dichvu/thanh-toan";
    }

    @PostMapping("/thanh-toan")
    public String completePayment(@ModelAttribute om.healthmate.javaproject.entity.AppointmentDv appointment, HttpSession session) {
        appointment.setStatus("pending");
        appointment.setPaymentStatus("paid");
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj != null) {
            try {
                appointment.setUserId(Long.parseLong(userIdObj.toString()));
            } catch (Exception e) {
                // handle parse error if needed
            }
        }
        appointmentDvService.save(appointment);
        return "redirect:/dichvu/hoan-thanh-dat-kham";
    }

    @GetMapping("/hoan-thanh-dat-kham")
    public String hoanThanhDatKhamPage() {
        return "dichvu/hoan-thanh-dat-kham";
    }

    @GetMapping("/{id}")
    public String serviceDetail(@PathVariable Long id, Model model) {
        HealthService service = healthServiceService.getById(id);
        model.addAttribute("service", service);
        return "dichvu/chi-tiet-dich-vu";
    }
} 