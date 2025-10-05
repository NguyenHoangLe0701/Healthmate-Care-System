package om.healthmate.javaproject.service;

import om.healthmate.javaproject.entity.ServiceLocation;
import om.healthmate.javaproject.repository.ServiceLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLocationService {
    @Autowired
    private ServiceLocationRepository serviceLocationRepository;

    public List<ServiceLocation> getAllLocations() {
        return serviceLocationRepository.findAll();
    }
} 