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

    public Cycle save(Cycle cycle) {
        return cycleRepository.save(cycle);
    }

    public List<Cycle> getCyclesByUser(User user) {
        return cycleRepository.findByUser(user);
    }

    // Ngày rụng trứng (startDate + cycleLength - 14)
    public LocalDate getOvulationDate(Cycle cycle) {
        return cycle.getStartDate().plusDays(cycle.getCycleLength() - 14);
    }

    // Cửa sổ thụ thai (5 ngày trước và 1 ngày sau rụng trứng)
    public LocalDate[] getFertileWindow(Cycle cycle) {
        LocalDate ovulation = getOvulationDate(cycle);
        return new LocalDate[] { ovulation.minusDays(5), ovulation.plusDays(1) };
    }

    // Lịch uống thuốc tránh thai (toàn bộ chu kỳ, dạng LocalDate)
    public List<LocalDate> getPillReminderDates(Cycle cycle) {
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
        return pillReminderRepository.findByCycle(cycle);
    }

    // Trả về nhãn cho từng ngày quan trọng trong chu kỳ (key là String yyyy-MM-dd)
    public Map<String, String> getCycleDayLabels(Cycle cycle) {
        Map<String, String> labels = new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
        LocalDate start = cycle.getStartDate();
        int cycleLength = cycle.getCycleLength();
        int periodLength = cycle.getPeriodLength();
        LocalDate ovulation = getOvulationDate(cycle);
        LocalDate fertileStart = ovulation.minusDays(5);
        LocalDate fertileEnd = ovulation.plusDays(1);
        LocalDate tryTest = ovulation.plusDays(7);

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