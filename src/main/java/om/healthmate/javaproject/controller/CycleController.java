package om.healthmate.javaproject.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import om.healthmate.javaproject.entity.Cycle;
import om.healthmate.javaproject.entity.PillReminder;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.service.CycleService;
import om.healthmate.javaproject.service.PillReminderService;
import om.healthmate.javaproject.service.UserService;

@Controller
@RequestMapping("/cycles")
public class CycleController {
    @Autowired
    private CycleService cycleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PillReminderService pillReminderService;

    @GetMapping
    public String listCycles(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            model.addAttribute("showLoginOverlay", true); // Truyền biến để bật overlay đăng nhập
            return "index"; // Trả về trang chính, overlay sẽ tự bật
        }
        User user = userService.findByEmail(userEmail);
        List<Cycle> cycles = cycleService.getCyclesByUser(user);
        model.addAttribute("cycles", cycles);
        model.addAttribute("cycle", new Cycle());
        return "chuky/cycle-list";
    }

    @PostMapping("/add")
    public String addCycle(
        @ModelAttribute Cycle cycle,
        @RequestParam(value = "pillTime", required = false) String pillTime,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }
            
            cycle.setUser(user);
            
            // Kiểm tra trùng lặp
            Cycle duplicate = cycleService.findDuplicateCycle(user, cycle.getStartDate());
            if (duplicate != null) {
                redirectAttributes.addFlashAttribute("error", 
                    "Đã tồn tại chu kỳ với ngày bắt đầu này. Vui lòng cập nhật chu kỳ hiện có hoặc chọn ngày khác.");
                redirectAttributes.addFlashAttribute("duplicateCycleId", duplicate.getId());
                return "redirect:/cycles";
            }
            
            // Validate và save
            Cycle saved = cycleService.save(cycle);

            // Nếu người dùng chọn giờ thì dùng, không thì mặc định 20:00
            LocalTime time = LocalTime.of(20, 0);
            if (pillTime != null && !pillTime.isEmpty()) {
                try {
                    time = LocalTime.parse(pillTime);
                } catch (DateTimeParseException e) {
                    // Giữ mặc định nếu parse lỗi
                }
            }

            cycleService.generateAndSavePillReminders(saved, time);
            redirectAttributes.addFlashAttribute("success", "Tạo chu kỳ thành công!");

            // Sau khi lưu, chuyển hướng sang trang chi tiết chu kỳ vừa tạo
            return "redirect:/cycles/details/" + saved.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cycles";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    // Thêm API tạo lịch nhắc cho chu kỳ đã có
    @PostMapping("/add-reminder")
    public String addPillReminder(
        @RequestParam("cycleId") Long cycleId,
        @RequestParam("method") String method,
        @RequestParam(value = "pillTime", required = false) String pillTime,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }

            // Lấy chu kỳ đã có từ DB
            Cycle cycle = cycleService.getCyclesByUser(user)
                .stream().filter(c -> c.getId().equals(cycleId)).findFirst().orElse(null);
            if (cycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại");
                return "redirect:/cycles";
            }

            cycle.setMethod(method); // cập nhật loại thuốc nếu cần
            cycleService.save(cycle); // Lưu lại method

            LocalTime time = LocalTime.of(20, 0);
            if (pillTime != null && !pillTime.isEmpty()) {
                try {
                    time = LocalTime.parse(pillTime);
                } catch (DateTimeParseException e) {
                    // Giữ mặc định nếu parse lỗi
                }
            }

            cycleService.generateAndSavePillReminders(cycle, time);
            redirectAttributes.addFlashAttribute("success", "Tạo lịch nhắc thành công!");

            return "redirect:/cycles/details/" + cycle.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    // API xóa lịch nhắc
    @PostMapping("/delete-reminders")
    public String deleteMultipleReminders(
        @RequestParam("reminderIds") List<Long> reminderIds,
        @RequestParam("cycleId") Long cycleId,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }
            
            // Kiểm tra quyền truy cập
            Cycle cycle = cycleService.getCyclesByUser(user)
                .stream().filter(c -> c.getId().equals(cycleId)).findFirst().orElse(null);
            if (cycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại hoặc bạn không có quyền truy cập");
                return "redirect:/cycles";
            }
            
            if (reminderIds != null && !reminderIds.isEmpty()) {
                for (Long id : reminderIds) {
                    pillReminderService.deleteById(id);
                }
                redirectAttributes.addFlashAttribute("success", "Đã xóa " + reminderIds.size() + " lịch nhắc");
            }
            return "redirect:/cycles/details/" + cycleId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    // API chỉnh sửa chu kỳ
    @GetMapping("/edit/{id}")
    public String showEditCycleForm(
        @PathVariable Long id,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }

            Cycle cycle = cycleService.getCyclesByUser(user)
                .stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
            if (cycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại hoặc bạn không có quyền truy cập");
                return "redirect:/cycles";
            }

            model.addAttribute("cycle", cycle);
            model.addAttribute("cycles", cycleService.getCyclesByUser(user));
            return "chuky/cycle-list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateCycle(
        @PathVariable Long id,
        @ModelAttribute Cycle cycle,
        @RequestParam(value = "pillTime", required = false) String pillTime,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }

            Cycle existingCycle = cycleService.getCyclesByUser(user)
                .stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
            if (existingCycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại hoặc bạn không có quyền truy cập");
                return "redirect:/cycles";
            }

            // Kiểm tra trùng lặp (trừ chính nó)
            Cycle duplicate = cycleService.findDuplicateCycle(user, cycle.getStartDate());
            if (duplicate != null && !duplicate.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", 
                    "Đã tồn tại chu kỳ khác với ngày bắt đầu này. Vui lòng chọn ngày khác.");
                return "redirect:/cycles/edit/" + id;
            }

            // Cập nhật thông tin
            existingCycle.setStartDate(cycle.getStartDate());
            existingCycle.setCycleLength(cycle.getCycleLength());
            existingCycle.setPeriodLength(cycle.getPeriodLength());
            existingCycle.setMethod(cycle.getMethod());
            existingCycle.setUser(user);

            Cycle saved = cycleService.save(existingCycle);

            // Cập nhật lịch nhắc nếu có
            LocalTime time = LocalTime.of(20, 0);
            if (pillTime != null && !pillTime.isEmpty()) {
                try {
                    time = LocalTime.parse(pillTime);
                } catch (DateTimeParseException e) {
                    // Giữ mặc định nếu parse lỗi
                }
            }
            cycleService.generateAndSavePillReminders(saved, time);

            redirectAttributes.addFlashAttribute("success", "Cập nhật chu kỳ thành công!");
            return "redirect:/cycles/details/" + saved.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cycles/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    // API xóa chu kỳ
    @PostMapping("/delete/{id}")
    public String deleteCycle(
        @PathVariable Long id,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }

            Cycle cycle = cycleService.getCyclesByUser(user)
                .stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
            if (cycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại hoặc bạn không có quyền truy cập");
                return "redirect:/cycles";
            }

            cycleService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa chu kỳ thành công!");
            return "redirect:/cycles";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }

    @GetMapping("/details/{id}")
    public String cycleDetails(
            @PathVariable Long id,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra đăng nhập
            String userEmail = (String) session.getAttribute("userEmail");
            if (userEmail == null) return "redirect:/auth/login";
            User user = userService.findByEmail(userEmail);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                return "redirect:/cycles";
            }

            // Lấy chu kỳ theo user và id
            Cycle cycle = cycleService.getCyclesByUser(user)
                    .stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
            if (cycle == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không tồn tại hoặc bạn không có quyền truy cập");
                return "redirect:/cycles";
            }
            
            if (cycle.getStartDate() == null) {
                redirectAttributes.addFlashAttribute("error", "Chu kỳ không hợp lệ");
                return "redirect:/cycles";
            }

        // Xác định tháng/năm hiển thị và giới hạn chỉ 5 tháng liên tiếp (2 tháng trước, tháng hiện tại, 2 tháng sau)
        LocalDate baseDate = cycle.getStartDate();
        int baseMonth = baseDate.getMonthValue();
        int baseYear = baseDate.getYear();

        int showMonth = (month != null) ? month : baseMonth;
        int showYear = (year != null) ? year : baseYear;

        YearMonth baseYM = YearMonth.of(baseYear, baseMonth);
        YearMonth showYM = YearMonth.of(showYear, showMonth);
        long monthsBetween = ChronoUnit.MONTHS.between(baseYM, showYM);
        if (monthsBetween < -2 || monthsBetween > 2) {
            // Nếu ngoài phạm vi 5 tháng (2 trước, 2 sau), chuyển về tháng bắt đầu
            return "redirect:/cycles/details/" + id + "?month=" + baseMonth + "&year=" + baseYear;
        }

        YearMonth ym = YearMonth.of(showYear, showMonth);
        LocalDate firstDay = ym.atDay(1);
        LocalDate lastDay = ym.atEndOfMonth();

        // Tạo danh sách các tuần (mỗi tuần là List<LocalDate>)
        List<List<LocalDate>> weeks = new ArrayList<>();
        List<LocalDate> week = new ArrayList<>();
        int dayOfWeekValue = firstDay.getDayOfWeek().getValue() % 7; // CN=0, T2=1,...
        for (int i = 0; i < dayOfWeekValue; i++) week.add(null);
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            week.add(date);
            if (week.size() == 7) {
                weeks.add(week);
                week = new ArrayList<>();
            }
        }
        if (!week.isEmpty()) {
            while (week.size() < 7) week.add(null);
            weeks.add(week);
        }

            // Lấy nhãn cho nhiều chu kỳ liên tiếp (bao phủ các tháng trước/sau)
            Map<String, String> cycleDayLabels = cycleService.getMultiCycleDayLabels(cycle, 2, 2);

            // Lấy danh sách PillReminder entity (có cả ngày và giờ nhắc)
            List<PillReminder> pillReminders = cycleService.getPillRemindersByCycle(cycle);

            // Tính toán các ngày quan trọng với error handling
            LocalDate ovulation = null;
            LocalDate[] fertileWindow = null;
            try {
                ovulation = cycleService.getOvulationDate(cycle);
                fertileWindow = cycleService.getFertileWindow(cycle);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", "Không thể tính toán ngày rụng trứng: " + e.getMessage());
                return "redirect:/cycles";
            }

            // Thêm dữ liệu vào model cho Thymeleaf
            model.addAttribute("cycle", cycle);
            model.addAttribute("ovulation", ovulation);
            model.addAttribute("fertileWindow", fertileWindow);
            model.addAttribute("pillReminders", pillReminders);
            model.addAttribute("cycleDayLabels", cycleDayLabels);
            model.addAttribute("futureCycles", cycleService.getFutureCycleDayLabels(cycle, 3));
            model.addAttribute("weeks", weeks);
            model.addAttribute("showMonth", showMonth);
            model.addAttribute("showYear", showYear);

        // Tháng trước và sau (chỉ cho phép trong phạm vi 5 tháng: -2, -1, 0, +1, +2)
        YearMonth prev2 = ym.minusMonths(2);
        YearMonth prev = ym.minusMonths(1);
        YearMonth next = ym.plusMonths(1);
        YearMonth next2 = ym.plusMonths(2);

        if (ChronoUnit.MONTHS.between(baseYM, prev2) < -2) {
            model.addAttribute("prev2Month", null);
            model.addAttribute("prev2Year", null);
        } else {
            model.addAttribute("prev2Month", prev2.getMonthValue());
            model.addAttribute("prev2Year", prev2.getYear());
        }
        if (ChronoUnit.MONTHS.between(baseYM, prev) < -2) {
            model.addAttribute("prevMonth", null);
            model.addAttribute("prevYear", null);
        } else {
            model.addAttribute("prevMonth", prev.getMonthValue());
            model.addAttribute("prevYear", prev.getYear());
        }
        if (ChronoUnit.MONTHS.between(baseYM, next) > 2) {
            model.addAttribute("nextMonth", null);
            model.addAttribute("nextYear", null);
        } else {
            model.addAttribute("nextMonth", next.getMonthValue());
            model.addAttribute("nextYear", next.getYear());
        }
        if (ChronoUnit.MONTHS.between(baseYM, next2) > 2) {
            model.addAttribute("next2Month", null);
            model.addAttribute("next2Year", null);
        } else {
            model.addAttribute("next2Month", next2.getMonthValue());
            model.addAttribute("next2Year", next2.getYear());
        }

            return "chuky/cycle-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/cycles";
        }
    }
}