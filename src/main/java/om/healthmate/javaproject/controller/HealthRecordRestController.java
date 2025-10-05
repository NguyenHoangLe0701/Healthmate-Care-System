package om.healthmate.javaproject.controller;

import om.healthmate.javaproject.entity.HealthRecord;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.service.HealthRecordService;
import om.healthmate.javaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordRestController {
    
    @Autowired
    private HealthRecordService healthRecordService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HealthRecord> getHealthRecordById(@PathVariable Long id) {
        HealthRecord record = healthRecordService.findById(id);
        if (record != null) {
            return ResponseEntity.ok(record);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createHealthRecord(@RequestBody HealthRecordRequest request) {
        try {
            User user = userService.findById(request.getUserId());
            if (user == null) {
                return ResponseEntity.badRequest().body("User không tồn tại");
            }
            
            HealthRecord record = new HealthRecord();
            record.setUser(user);
            record.setHeight(request.getHeight());
            record.setWeight(request.getWeight());
            record.setBloodPressure(request.getBloodPressure());
            record.setHeartRate(request.getHeartRate());
            record.setBloodType(request.getBloodType());
            record.setAllergies(request.getAllergies());
            record.setMedications(request.getMedications());
            record.setMedicalHistory(request.getMedicalHistory());
            
            HealthRecord savedRecord = healthRecordService.save(record);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHealthRecord(@PathVariable Long id, @RequestBody HealthRecordRequest request) {
        try {
            HealthRecord existingRecord = healthRecordService.findById(id);
            if (existingRecord == null) {
                return ResponseEntity.notFound().build();
            }
            
            User user = userService.findById(request.getUserId());
            if (user == null) {
                return ResponseEntity.badRequest().body("User không tồn tại");
            }
            
            existingRecord.setUser(user);
            existingRecord.setHeight(request.getHeight());
            existingRecord.setWeight(request.getWeight());
            existingRecord.setBloodPressure(request.getBloodPressure());
            existingRecord.setHeartRate(request.getHeartRate());
            existingRecord.setBloodType(request.getBloodType());
            existingRecord.setAllergies(request.getAllergies());
            existingRecord.setMedications(request.getMedications());
            existingRecord.setMedicalHistory(request.getMedicalHistory());
            
            HealthRecord updatedRecord = healthRecordService.save(existingRecord);
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHealthRecord(@PathVariable Long id) {
        try {
            HealthRecord record = healthRecordService.findById(id);
            if (record == null) {
                return ResponseEntity.notFound().build();
            }
            
        healthRecordService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
    
    // Inner class để nhận request data
    public static class HealthRecordRequest {
        private Long userId;
        private Double height;
        private Double weight;
        private String bloodPressure;
        private Integer heartRate;
        private String bloodType;
        private String allergies;
        private String medications;
        private String medicalHistory;
        
        // Getters and setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public Double getHeight() { return height; }
        public void setHeight(Double height) { this.height = height; }
        
        public Double getWeight() { return weight; }
        public void setWeight(Double weight) { this.weight = weight; }
        
        public String getBloodPressure() { return bloodPressure; }
        public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }
        
        public Integer getHeartRate() { return heartRate; }
        public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
        
        public String getBloodType() { return bloodType; }
        public void setBloodType(String bloodType) { this.bloodType = bloodType; }
        
        public String getAllergies() { return allergies; }
        public void setAllergies(String allergies) { this.allergies = allergies; }
        
        public String getMedications() { return medications; }
        public void setMedications(String medications) { this.medications = medications; }
        
        public String getMedicalHistory() { return medicalHistory; }
        public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    }
} 