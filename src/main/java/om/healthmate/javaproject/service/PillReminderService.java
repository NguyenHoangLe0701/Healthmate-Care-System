package om.healthmate.javaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.repository.PillReminderRepository;

@Service
public class PillReminderService {

    @Autowired
    private PillReminderRepository pillReminderRepository;

    public void deleteById(Long id) {
        pillReminderRepository.deleteById(id);
    }
}