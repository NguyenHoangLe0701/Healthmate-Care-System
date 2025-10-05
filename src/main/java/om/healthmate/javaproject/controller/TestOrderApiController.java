package om.healthmate.javaproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import om.healthmate.javaproject.entity.TestOrder;
import om.healthmate.javaproject.service.TestOrderService;

@RestController
@RequestMapping("/api/test-orders")
public class TestOrderApiController {
    @Autowired
    private TestOrderService testOrderService;

    // Lấy tất cả đơn xét nghiệm của 1 user (khách hàng)
    @GetMapping("/user/{userId}")
    public List<TestOrder> getOrdersByUser(@PathVariable Long userId) {
        return testOrderService.getOrdersByUser(userId);
    }

    // Lấy chi tiết 1 đơn xét nghiệm
    @GetMapping("/{id}")
    public TestOrder getOrder(@PathVariable Long id) {
        Optional<TestOrder> order = testOrderService.getOrder(id);
        return order.orElse(null);
    }

    // Lấy tất cả đơn xét nghiệm (nếu cần)
    @GetMapping("")
    public List<TestOrder> getAllOrders() {
        return testOrderService.getAllOrders();
    }
}
