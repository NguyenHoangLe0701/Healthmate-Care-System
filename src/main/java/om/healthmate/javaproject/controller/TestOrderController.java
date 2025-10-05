package om.healthmate.javaproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import om.healthmate.javaproject.entity.TestOrder;
import om.healthmate.javaproject.service.TestOrderService;

@Controller
@RequestMapping("/test-orders")
public class TestOrderController {
    // Xử lý truy cập /xn/dat-lich không có orderId (tránh lỗi 404 static resource)
    @org.springframework.web.bind.annotation.GetMapping(value = "/xn/dat-lich", params = "!orderId")
    public String showBookingNoId(org.springframework.ui.Model model) {
        model.addAttribute("error", "Thiếu mã đơn xét nghiệm!");
        return "error";
    }
    // Đã loại bỏ hoàn toàn danh sách đơn đã thanh toán (không còn luồng thanh toán)
    // Xử lý lỗi chuyển đổi kiểu tham số (ví dụ userId=null hoặc sai kiểu)
    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập trước khi đặt lịch xét nghiệm!");
        return "redirect:/test-orders/book";
    }
    @Autowired
    private TestOrderService testOrderService;

    // Hiển thị form đặt lịch xét nghiệm
    // Địa điểm xét nghiệm tại Bệnh viện Bệnh Nhiệt Đới TP.HCM và các cơ sở
    private static final java.util.Map<String, String[]> LOCATION_MAP = java.util.Map.of(
        "HIV", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "Giang mai", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "Lậu", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "Chlamydia", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "Herpes", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "Trichomonas", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        },
        "HPV", new String[]{
            "Bệnh viện Bệnh Nhiệt Đới - 764 Võ Văn Kiệt, Phường 1, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 217 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
            "Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM"
        }
    );


    @GetMapping("/book")
    public String showBookingForm(Model model, HttpSession session) {
        model.addAttribute("testOrder", new TestOrder());
        model.addAttribute("locationMap", LOCATION_MAP);
        // Đảm bảo Thymeleaf nhận đúng kiểu boolean cho sessionUserId == null
        Object userId = session.getAttribute("userId");
        model.addAttribute("sessionUserId", userId); // Truyền null nếu chưa đăng nhập
        // Có thể truyền role nếu cần
        Object role = session.getAttribute("role");
        model.addAttribute("sessionRole", role);
        // Truyền thêm biến boolean để Thymeleaf render đúng JS
        model.addAttribute("notLoggedIn", userId == null);
        return "testorder/testorder-book";
    }

    // Xử lý đặt lịch xét nghiệm
    @PostMapping("/book")
    public String bookTest(@ModelAttribute TestOrder testOrder, HttpSession session, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập trước khi đặt lịch xét nghiệm!");
            return "redirect:/test-orders/book";
        }
        Long userId;
        try {
            userId = Long.valueOf(userIdObj.toString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Tài khoản không hợp lệ. Vui lòng đăng nhập lại!");
            return "redirect:/test-orders/book";
        }
        // Tạo đơn xét nghiệm
        testOrderService.createTestOrder(
            userId,
            testOrder.getTestType(),
            testOrder.getLocation(),
            testOrder.getAppointmentTime()
        );
        return "redirect:/test-orders/list";
    }

    // Xem danh sách đơn xét nghiệm của user
    @GetMapping("/list")
    public String listOrders(@RequestParam(required = false) Long userId, Model model, HttpSession session) {
        // Ưu tiên lấy userId từ param, nếu không có thì lấy từ session
        if (userId == null) {
            Object sessionUserId = session.getAttribute("userId");
            if (sessionUserId == null) {
                // Nếu chưa đăng nhập thì chuyển về login
                return "redirect:/login";
            }
            userId = Long.valueOf(sessionUserId.toString());
        }
        List<TestOrder> orders = testOrderService.getOrdersByUser(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("sessionUserId", userId);
        return "testorder/testorder-list";
    }

    // Xem chi tiết đơn xét nghiệm
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Optional<TestOrder> order = testOrderService.getOrder(id);
        order.ifPresent(o -> model.addAttribute("order", o));
        return "testorder/testorder-detail";
    }
    // Đã thay thế bằng overlay/modal trong doctor-testorder-list.html, không còn sử dụng form riêng

    // Xử lý cập nhật trạng thái đơn xét nghiệm
    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        testOrderService.updateStatus(id, status);
        return "redirect:/test-orders/detail/" + id;
    }
        // Hiển thị form nhập kết quả động theo loại xét nghiệm
    @GetMapping("/enter-result/{id}")
    public String showResultForm(@PathVariable Long id, Model model) {
        Optional<TestOrder> order = testOrderService.getOrder(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            // Có thể truyền thêm các thông tin đặc thù từng loại xét nghiệm nếu muốn
            return "testorder/result-form";
        }
        return "redirect:/test-orders/list";
    }

    // Xử lý lưu kết quả xét nghiệm
    @PostMapping("/enter-result/{id}")
    public String saveResult(
        @PathVariable Long id,
        @RequestParam String result,
        @RequestParam(required = false) String note,
        @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile
    ) {
        // Lưu kết quả và ghi chú
        testOrderService.setResult(id, result, note);

        // Xử lý lưu file PDF nếu có
        if (pdfFile != null && !pdfFile.isEmpty()) {
            try {
                String uploadDir = "uploads/pdf/";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }
                String fileName = "testorder-" + id + "-" + System.currentTimeMillis() + ".pdf";
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                pdfFile.transferTo(filePath.toFile());
                // Lưu đường dẫn file vào DB (giả sử TestOrder có trường pdfPath)
                testOrderService.setPdfPath(id, "/" + uploadDir + fileName);
            } catch (java.io.IOException | IllegalStateException e) {
                // Xử lý lỗi nếu cần, ví dụ ghi log lỗi
                org.slf4j.LoggerFactory.getLogger(TestOrderController.class).error("Lỗi khi lưu file PDF kết quả xét nghiệm", e);
            }
        }
        return "redirect:/test-orders/detail/" + id;
    }
        // Danh sách đơn xét nghiệm cho bác sĩ (tất cả hoặc lọc theo trạng thái)
    @GetMapping("/doctor-list")
    public String doctorList(Model model, HttpSession session) {
        java.util.List<om.healthmate.javaproject.entity.TestOrder> orders = testOrderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "testorder/doctor-testorder-list";
    }

    // Hỗ trợ truy cập chi tiết đơn bằng query param: /test-orders/detail?orderId=...
    @GetMapping("/detail")
    public String orderDetailByParam(@RequestParam("orderId") Long orderId, Model model) {
        return orderDetail(orderId, model);
    }

    // Thêm endpoint hiển thị lịch đã đặt (không còn thanh toán)
    @org.springframework.web.bind.annotation.GetMapping("/xn/dat-lich")
    public String showBooking(@org.springframework.web.bind.annotation.RequestParam("orderId") Long orderId, org.springframework.ui.Model model) {
        TestOrder order = testOrderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Không tìm thấy đơn xét nghiệm");
            return "error";
        }
        model.addAttribute("order", order);
        // Trả về đúng file booking.html (không cần file dat-lich.html)
        return "xn/booking";
    }
    // Hiển thị form xác nhận trạng thái và nhập kết quả cho bác sĩ
    @org.springframework.web.bind.annotation.GetMapping("/xn/xac-nhan")
    public String showDoctorConfirm(@org.springframework.web.bind.annotation.RequestParam("orderId") Long orderId, org.springframework.ui.Model model) {
        TestOrder order = testOrderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Không tìm thấy đơn xét nghiệm");
            return "error";
        }
        model.addAttribute("order", order);
        // Sử dụng template trong folder testorder
        return "testorder/result-form";
    }

    // Xử lý POST xác nhận trạng thái và nhập kết quả
    @org.springframework.web.bind.annotation.PostMapping("/xn/xac-nhan")
    public String doctorConfirm(
        @org.springframework.web.bind.annotation.RequestParam("orderId") Long orderId,
        @org.springframework.web.bind.annotation.RequestParam("status") String status,
        @org.springframework.web.bind.annotation.RequestParam(value = "result", required = false) String result,
        @org.springframework.web.bind.annotation.RequestParam(value = "note", required = false) String note,
        org.springframework.web.multipart.MultipartFile pdfFile,
        org.springframework.ui.Model model
    ) {
        TestOrder order = testOrderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "Không tìm thấy đơn xét nghiệm");
            return "error";
        }
        order.setStatus(status);
        if (result != null && !result.isEmpty()) order.setResult(result);
        if (note != null) order.setNote(note);
        // Xử lý lưu file PDF nếu có
        if (pdfFile != null && !pdfFile.isEmpty()) {
            try {
                String fileName = "order_" + orderId + ".pdf";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get("uploads/pdf");
                if (!java.nio.file.Files.exists(uploadPath)) java.nio.file.Files.createDirectories(uploadPath);
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                pdfFile.transferTo(filePath.toFile());
                order.setPdfPath(fileName);
            } catch (Exception e) {
                model.addAttribute("error", "Lỗi khi lưu file PDF: " + e.getMessage());
                return "error";
            }
        }
        testOrderService.save(order);
        return "redirect:/xn/dat-lich?orderId=" + orderId;
    }
}
