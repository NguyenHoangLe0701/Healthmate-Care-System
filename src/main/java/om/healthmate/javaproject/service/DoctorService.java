package om.healthmate.javaproject.service;

import java.util.List;

import om.healthmate.javaproject.entity.Doctor;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long id);
    Doctor saveDoctor(Doctor doctor);
    Doctor findById(Long id);
    void deleteDoctor(Long id);
}
