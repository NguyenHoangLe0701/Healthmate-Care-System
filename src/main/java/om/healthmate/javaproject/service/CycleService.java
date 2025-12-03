package om.healthmate.javaproject.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import om.healthmate.javaproject.entity.Cycle;
import om.healthmate.javaproject.entity.PillReminder;
import om.healthmate.javaproject.entity.User;
import om.healthmate.javaproject.repository.CycleRepository;
import om.healthmate.javaproject.repository.PillReminderRepository;

@Service
public class CycleService {
    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private PillReminderRepository pillReminderRepository;

    // Hằng số validation
    private static final int MIN_CYCLE_LENGTH = 20;
    private static final int MAX_CYCLE_LENGTH = 40;
    private static final int MIN_PERIOD_LENGTH = 3;
    private static final int MAX_PERIOD_LENGTH = 7;
    private static final int MIN_OVULATION_DAYS = 14; // Chu kỳ phải ít nhất 14 ngày để tính rụng trứng

    /**
     * Validate cycle data
     * @return null if valid, error message if invalid
     */
    public String validateCycle(Cycle cycle) {
        if (cycle == null) {
            return "Chu kỳ không được để trống";
        }
        if (cycle.getStartDate() == null) {
            return "Ngày bắt đầu chu kỳ không được để trống";
        }
        if (cycle.getStartDate().isAfter(LocalDate.now())) {
            return "Ngày bắt đầu chu kỳ không được trong tương lai";
        }
        if (cycle.getStartDate().isBefore(LocalDate.now().minusYears(1))) {
            return "Ngày bắt đầu chu kỳ không được quá 1 năm trong quá khứ";
        }
        if (cycle.getCycleLength() < MIN_CYCLE_LENGTH || cycle.getCycleLength() > MAX_CYCLE_LENGTH) {
            return String.format("Độ dài chu kỳ phải từ %d đến %d ngày", MIN_CYCLE_LENGTH, MAX_CYCLE_LENGTH);
        }
        if (cycle.getPeriodLength() < MIN_PERIOD_LENGTH || cycle.getPeriodLength() > MAX_PERIOD_LENGTH) {
            return String.format("Số ngày hành kinh phải từ %d đến %d ngày", MIN_PERIOD_LENGTH, MAX_PERIOD_LENGTH);
        }
        if (cycle.getPeriodLength() >= cycle.getCycleLength()) {
            return "Số ngày hành kinh phải nhỏ hơn độ dài chu kỳ";
        }
        if (cycle.getCycleLength() < MIN_OVULATION_DAYS) {
            return String.format("Độ dài chu kỳ phải ít nhất %d ngày để tính ngày rụng trứng", MIN_OVULATION_DAYS);
        }
        return null; // Valid
    }

    public Cycle save(Cycle cycle) {
        String validationError = validateCycle(cycle);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }
        return cycleRepository.save(cycle);
    }

    public Cycle findById(Long id) {
        return cycleRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        Cycle cycle = findById(id);
        if (cycle != null) {
            // Xóa tất cả PillReminder liên quan
            List<PillReminder> reminders = getPillRemindersByCycle(cycle);
            for (PillReminder reminder : reminders) {
                pillReminderRepository.delete(reminder);
            }
            cycleRepository.delete(cycle);
        }
    }

    public List<Cycle> getCyclesByUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return cycleRepository.findByUser(user);
    }

    /**
     * Kiểm tra xem có chu kỳ nào trùng startDate không
     */
    public Cycle findDuplicateCycle(User user, LocalDate startDate) {
        if (user == null || startDate == null) {
            return null;
        }
        List<Cycle> cycles = getCyclesByUser(user);
        return cycles.stream()
                .filter(c -> c.getStartDate().equals(startDate))
                .findFirst()
                .orElse(null);
    }

    // Ngày rụng trứng (startDate + cycleLength - 14)
    public LocalDate getOvulationDate(Cycle cycle) {
        if (cycle == null || cycle.getStartDate() == null) {
            throw new IllegalArgumentException("Cycle và startDate không được null");
        }
        if (cycle.getCycleLength() < MIN_OVULATION_DAYS) {
            throw new IllegalArgumentException("Chu kỳ quá ngắn để tính ngày rụng trứng");
        }
        return cycle.getStartDate().plusDays(cycle.getCycleLength() - 14);
    }

    // Cửa sổ thụ thai (5 ngày trước và 1 ngày sau rụng trứng)
    public LocalDate[] getFertileWindow(Cycle cycle) {
        if (cycle == null) {
            throw new IllegalArgumentException("Cycle không được null");
        }
        LocalDate ovulation = getOvulationDate(cycle);
        LocalDate fertileStart = ovulation.minusDays(5);
        LocalDate fertileEnd = ovulation.plusDays(1);
        
        // Đảm bảo cửa sổ thụ thai không vượt quá chu kỳ
        LocalDate cycleEnd = cycle.getStartDate().plusDays(cycle.getCycleLength() - 1);
        if (fertileEnd.isAfter(cycleEnd)) {
            fertileEnd = cycleEnd;
        }
        if (fertileStart.isBefore(cycle.getStartDate())) {
            fertileStart = cycle.getStartDate();
        }
        
        return new LocalDate[] { fertileStart, fertileEnd };
    }

    // Lịch uống thuốc tránh thai (toàn bộ chu kỳ, dạng LocalDate)
    public List<LocalDate> getPillReminderDates(Cycle cycle) {
        if (cycle == null || cycle.getStartDate() == null) {
            return new ArrayList<>();
        }
        String pillType = cycle.getMethod();
        LocalDate start = cycle.getStartDate();
        int count = "PILL_21".equalsIgnoreCase(pillType) ? 21 :
                    "PILL_28".equalsIgnoreCase(pillType) ? 28 :
                    cycle.getCycleLength();
        List<LocalDate> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(start.plusDays(i));
        }
        return result;
    }

    // Sinh và lưu danh sách PillReminder cho một Cycle
    public void generateAndSavePillReminders(Cycle cycle, LocalTime defaultTime) {
        if (cycle == null || cycle.getStartDate() == null) {
            throw new IllegalArgumentException("Cycle và startDate không được null");
        }
        // Xóa các reminder cũ nếu có
        List<PillReminder> existingReminders = getPillRemindersByCycle(cycle);
        for (PillReminder reminder : existingReminders) {
            pillReminderRepository.delete(reminder);
        }
        
        String pillType = cycle.getMethod();
        LocalDate start = cycle.getStartDate();
        int count = "PILL_21".equalsIgnoreCase(pillType) ? 21 :
                    "PILL_28".equalsIgnoreCase(pillType) ? 28 :
                    cycle.getCycleLength();

        for (int i = 0; i < count; i++) {
            PillReminder reminder = new PillReminder(start.plusDays(i), defaultTime, cycle);
            pillReminderRepository.save(reminder);
        }
    }

    // Lấy danh sách PillReminder theo Cycle
    public List<PillReminder> getPillRemindersByCycle(Cycle cycle) {
        if (cycle == null) {
            return new ArrayList<>();
        }
        return pillReminderRepository.findByCycle(cycle);
    }

    // Trả về nhãn cho từng ngày quan trọng trong chu kỳ (key là String yyyy-MM-dd)
    public Map<String, String> getCycleDayLabels(Cycle cycle) {
        if (cycle == null || cycle.getStartDate() == null) {
            return new HashMap<>();
        }
        Map<String, String> labels = new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
        LocalDate start = cycle.getStartDate();
        int cycleLength = cycle.getCycleLength();
        int periodLength = cycle.getPeriodLength();
        
        // Xử lý edge case: periodLength không được >= cycleLength
        if (periodLength >= cycleLength) {
            periodLength = Math.min(periodLength, cycleLength - 1);
        }
        
        LocalDate ovulation = getOvulationDate(cycle);
        LocalDate fertileStart = ovulation.minusDays(5);
        LocalDate fertileEnd = ovulation.plusDays(1);
        LocalDate tryTest = ovulation.plusDays(7);
        
        // Đảm bảo các ngày không vượt quá chu kỳ
        LocalDate cycleEnd = start.plusDays(cycleLength - 1);
        if (fertileStart.isBefore(start)) {
            fertileStart = start;
        }
        if (fertileEnd.isAfter(cycleEnd)) {
            fertileEnd = cycleEnd;
        }
        if (tryTest.isAfter(cycleEnd)) {
            tryTest = cycleEnd;
        }

        // 1. Ngày có kinh nguyệt
        for (int i = 0; i < periodLength; i++) {
            labels.put(start.plusDays(i).format(fmt), "Ngày có kinh nguyệt");
        }
        // 2. Ngày bắt đầu có khả năng thụ thai
        labels.put(fertileStart.format(fmt), "Ngày bắt đầu có khả năng thụ thai");
        // 3. Ngày có khả năng thụ thai (ngày thứ hai trong giai đoạn)
        labels.put(fertileStart.plusDays(1).format(fmt), "Ngày có khả năng thụ thai (ngày thứ hai)");
        // 4. Ngày có khả năng thụ thai (hai ngày trước rụng trứng)
        labels.put(ovulation.minusDays(2).format(fmt), "Ngày có khả năng thụ thai (hai ngày trước rụng trứng)");
        // 5. Ngày có khả năng thụ thai cao (một ngày trước rụng trứng)
        labels.put(ovulation.minusDays(1).format(fmt), "Ngày có khả năng thụ thai cao");
        // 6. Ngày trứng rụng, có khả năng thụ thai cao nhất
        labels.put(ovulation.format(fmt), "Ngày trứng rụng, có khả năng thụ thai cao nhất");
        // 7. Tỷ lệ thụ thai giảm
        labels.put(ovulation.plusDays(1).format(fmt), "Tỷ lệ thụ thai giảm");

        // 8. Ngày an toàn tương đối (sau hành kinh, trước cửa sổ thụ thai)
        for (LocalDate d = start.plusDays(periodLength); d.isBefore(fertileStart); d = d.plusDays(1)) {
            labels.put(d.format(fmt), "Ngày an toàn tương đối");
        }
        // 9. Ngày an toàn tuyệt đối (cuối chu kỳ, sau cửa sổ thụ thai)
        for (LocalDate d = fertileEnd.plusDays(1); d.isBefore(start.plusDays(cycleLength)); d = d.plusDays(1)) {
            labels.put(d.format(fmt), "Ngày an toàn tuyệt đối");
        }
        // 10. Thử thai
        labels.put(tryTest.format(fmt), "Thử thai");

        // 11. Các ngày còn lại trong cửa sổ thụ thai (nếu có)
        for (LocalDate d = fertileStart.plusDays(2); !d.isAfter(ovulation.minusDays(3)); d = d.plusDays(1)) {
            if (!labels.containsKey(d.format(fmt))) {
                labels.put(d.format(fmt), "Ngày có khả năng thụ thai");
            }
        }
        return labels;
    }

    // Lấy danh sách các Map ngày/nhãn cho nhiều chu kỳ liên tiếp (ví dụ: nhiều tháng)
    public List<Map<String, String>> getFutureCycleDayLabels(Cycle cycle, int months) {
        if (cycle == null || cycle.getStartDate() == null) {
            return new ArrayList<>();
        }
        List<Map<String, String>> result = new ArrayList<>();
        LocalDate start = cycle.getStartDate();
        int cycleLength = cycle.getCycleLength();

        for (int i = 0; i < months; i++) {
            Cycle temp = new Cycle();
            temp.setStartDate(start.plusDays(i * cycleLength));
            temp.setCycleLength(cycleLength);
            temp.setPeriodLength(cycle.getPeriodLength());
            temp.setMethod(cycle.getMethod());
            temp.setUser(cycle.getUser());
            result.add(getCycleDayLabels(temp));
        }
        return result;
    }

    // Trả về nhãn cho từng ngày quan trọng trong nhiều chu kỳ liên tiếp (bao phủ nhiều tháng)
    public Map<String, String> getMultiCycleDayLabels(Cycle cycle, int beforeCycles, int afterCycles) {
        if (cycle == null || cycle.getStartDate() == null) {
            return new HashMap<>();
        }
        Map<String, String> labels = new HashMap<>();
        int cycleLength = cycle.getCycleLength();
        int periodLength = cycle.getPeriodLength();
        LocalDate baseStart = cycle.getStartDate();

        for (int i = -beforeCycles; i <= afterCycles; i++) {
            LocalDate start = baseStart.plusDays(i * cycleLength);
            Cycle temp = new Cycle();
            temp.setStartDate(start);
            temp.setCycleLength(cycleLength);
            temp.setPeriodLength(periodLength);
            temp.setMethod(cycle.getMethod());
            temp.setUser(cycle.getUser());
            Map<String, String> oneCycleLabels = getCycleDayLabels(temp);
            labels.putAll(oneCycleLabels);
        }
        return labels;
    }
}