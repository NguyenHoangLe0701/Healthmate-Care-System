package om.healthmate.javaproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.entity.TestOrder;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.TestOrderRepository;
import om.healthmate.javaproject.repository.UserRepository;

@Service
public class TestOrderService {
    @Autowired
    private TestOrderRepository testOrderRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả đơn xét nghiệm (cho bác sĩ)
    public List<TestOrder> getAllOrders() {
        return testOrderRepository.findAll();
    }


    public TestOrder createTestOrder(Long userId, String testType, String location, LocalDateTime appointmentTime) {
        TestOrder order = new TestOrder();
        order.setUserId(userId);
        order.setTestType(testType);
        order.setLocation(location);
        order.setAppointmentTime(appointmentTime);
        order.setStatus("BOOKED");
        order.setBookingTime(LocalDateTime.now());
        // Giá tiền theo loại xét nghiệm
        int price = switch (testType) {
            case "HIV" -> 500000;
            case "Giang mai" -> 700000;
            case "Lậu" -> 700000;
            case "Chlamydia" -> 800000;
            case "Herpes" -> 1000000;
            case "Trichomonas" -> 600000;
            case "HPV" -> 1500000;
            default -> 0;
        };
        order.setPrice(price);
        // Sinh mã khách hàng dạng IVIE-xxxxxx (6 số random)
        String code = "IVIE-" + String.format("%06d", (int)(Math.random()*1000000));
        order.setCustomerCode(code);
        // Lấy thông tin user để đồng bộ vào TestOrder
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            order.setFullName(user.getName());
            // Nếu có trường birthDate, gender trong User thì lấy luôn
            // order.setBirthDate(user.getBirthDate());
            // order.setGender(user.getGender());
        }
        return testOrderRepository.save(order);
    }

    public List<TestOrder> getOrdersByUser(Long userId) {
        return testOrderRepository.findByUserId(userId);
    }

    public Optional<TestOrder> getOrder(Long id) {
        return testOrderRepository.findById(id);
    }

    public TestOrder updateStatus(Long id, String status) {
        Optional<TestOrder> opt = testOrderRepository.findById(id);
        if (opt.isPresent()) {
            TestOrder order = opt.get();
            order.setStatus(status);
            if (status.equals("SAMPLE_COLLECTED")) {
                order.setSampleCollectedTime(LocalDateTime.now());
            }
            if (status.equals("RESULT_DELIVERED")) {
                order.setResultTime(LocalDateTime.now());
            }
            return testOrderRepository.save(order);
        }
        return null;
    }

    // Lưu kết quả và ghi chú
    public TestOrder setResult(Long id, String result, String note) {
        Optional<TestOrder> opt = testOrderRepository.findById(id);
        if (opt.isPresent()) {
            TestOrder order = opt.get();
            order.setResult(result);
            order.setNote(note);
            order.setStatus("RESULT_DELIVERED");
            order.setResultTime(LocalDateTime.now());
            return testOrderRepository.save(order);
        }
        return null;
    }

    // Lưu đường dẫn file PDF
    public void setPdfPath(Long id, String pdfPath) {
        Optional<TestOrder> opt = testOrderRepository.findById(id);
        if (opt.isPresent()) {
            TestOrder order = opt.get();
            order.setPdfPath(pdfPath);
            testOrderRepository.save(order);
        }
    }
        // Lấy đơn xét nghiệm theo id (bắt buộc trả về null nếu không có)
    public TestOrder findById(Long id) {
        return testOrderRepository.findById(id).orElse(null);
    }

    // Lưu đơn xét nghiệm
    public TestOrder save(TestOrder order) {
        return testOrderRepository.save(order);
    }
}
