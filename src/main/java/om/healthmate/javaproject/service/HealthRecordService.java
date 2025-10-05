package om.healthmate.javaproject.service;

import om.healthmate.javaproject.entity.HealthRecord;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.HealthRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class HealthRecordService {
    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private UserService userService;

    public HealthRecord save(HealthRecord record) {
        return healthRecordRepository.save(record);
    }

    public List<HealthRecord> findByUserId(Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            return healthRecordRepository.findByUser(user);
        }
        return List.of();
    }

    public HealthRecord getLatestByUserId(Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            List<HealthRecord> records = healthRecordRepository.findByUserOrderByIdDesc(user);
            return records.isEmpty() ? null : records.get(0);
        }
        return null;
    }

    public List<HealthRecord> findAll() {
        return healthRecordRepository.findAllWithUser();
    }

    public HealthRecord findById(Long id) {
        return healthRecordRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        healthRecordRepository.deleteById(id);
    }
} 