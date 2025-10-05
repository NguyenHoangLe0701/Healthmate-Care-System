package om.healthmate.javaproject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_orders")
public class TestOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String customerCode; // Mã khách hàng dạng IVIE-xxxxxx
    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    private String testType;
    private Integer price; // Giá tiền xét nghiệm
    private String location; // Địa điểm xét nghiệm
    private java.time.LocalDateTime appointmentTime; // Thời gian hẹn
    private String status;
    private LocalDateTime bookingTime;
    private LocalDateTime sampleCollectedTime;
    private LocalDateTime resultTime;
    private String result;
    private String note; // Ghi chú/tư vấn bác sĩ
    private String pdfPath; // Đường dẫn file PDF kết quả

    // Thông tin bổ sung cho giao diện
    private String fullName;
    private String gender;
    private java.time.LocalDate birthDate;
    private java.time.LocalDate resultDate;
    private String doctorSignature;
    public String getDoctorSignature() { return doctorSignature; }
    public void setDoctorSignature(String doctorSignature) { this.doctorSignature = doctorSignature; }

    // Getter & Setter
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public java.time.LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(java.time.LocalDate birthDate) { this.birthDate = birthDate; }
    public java.time.LocalDate getResultDate() { return resultDate; }
    public void setResultDate(java.time.LocalDate resultDate) { this.resultDate = resultDate; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public java.time.LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(java.time.LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public LocalDateTime getSampleCollectedTime() { return sampleCollectedTime; }
    public void setSampleCollectedTime(LocalDateTime sampleCollectedTime) { this.sampleCollectedTime = sampleCollectedTime; }
    public LocalDateTime getResultTime() { return resultTime; }
    public void setResultTime(LocalDateTime resultTime) { this.resultTime = resultTime; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }
}
