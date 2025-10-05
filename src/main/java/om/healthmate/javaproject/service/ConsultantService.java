package om.healthmate.javaproject.service;

import om.healthmate.javaproject.entity.Consultant;
import om.healthmate.javaproject.repository.ConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultantService {
    
    @Autowired
    private ConsultantRepository consultantRepository;
    
    public List<Consultant> findAll() {
        return consultantRepository.findAll();
    }
    
    public Consultant findById(Long id) {
        Optional<Consultant> consultant = consultantRepository.findById(id);
        return consultant.orElse(null);
    }
    
    public Consultant save(Consultant consultant) {
        return consultantRepository.save(consultant);
    }
    
    public void deleteById(Long id) {
        consultantRepository.deleteById(id);
    }
} 