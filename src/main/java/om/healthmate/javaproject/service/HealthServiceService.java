package om.healthmate.javaproject.service;

import om.healthmate.javaproject.entity.HealthService;
import om.healthmate.javaproject.repository.HealthServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthServiceService {
    @Autowired
    private HealthServiceRepository healthServiceRepository;

    public List<HealthService> getAllHealthServices() {
        return healthServiceRepository.findAll();
    }

    public List<HealthService> getHealthServicesByCategory(Long categoryId) {
        return healthServiceRepository.findByCategory_Id(categoryId);
    }

    public Page<HealthService> getAllHealthServices(Pageable pageable) {
        return healthServiceRepository.findAll(pageable);
    }

    public Page<HealthService> getHealthServicesByCategory(Long categoryId, Pageable pageable) {
        return healthServiceRepository.findByCategory_Id(categoryId, pageable);
    }

    public Page<HealthService> search(Long categoryId, Long locationId, String keyword, Pageable pageable) {
        return healthServiceRepository.search(categoryId, locationId, keyword, pageable);
    }

    public HealthService getById(Long id) {
        return healthServiceRepository.findById(id).orElse(null);
    }
} 