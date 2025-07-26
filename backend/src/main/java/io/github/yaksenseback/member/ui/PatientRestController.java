package io.github.yaksenseback.member.ui;

import io.github.yaksenseback.medication.domain.MedicationRecord;
import io.github.yaksenseback.medication.domain.TimeLabel;
import io.github.yaksenseback.member.application.PatientQueryService;
import io.github.yaksenseback.member.application.dto.MedicationScheduleDto;
import io.github.yaksenseback.member.application.dto.PatientDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientRestController {

    private final PatientQueryService patientQueryService;


    @GetMapping("/{id}")
    public PatientDetailDto getPatientDetail(@PathVariable Long id) {
        return patientQueryService.getPatientById(id);
    }

    @GetMapping("/{id}/medicationSchedules")
    public List<MedicationScheduleDto> getPatientMedicationSchedule(@PathVariable Long id) {
        return patientQueryService.getPatientMedicationSchedule(id);
    }

    @GetMapping("/{id}/medicationRecords")
    public List<MedicationRecord> getPatientMedicationRecords(@PathVariable Long id) {
        return patientQueryService.getPatientMedicationRecords(id);
    }

    /**
     * 복용해야할 약 목록
     * - 개발 편의를 위해 일자를 인자로 받는다.
     */
    @GetMapping("/{id}/medicationRecords/today")
    public List<MedicationScheduleDto> getTodayMedicationSchedule(@PathVariable Long id,
                                                                  @RequestParam TimeLabel timeLabel,
                                                                  @RequestParam(required = false) LocalDate date) {
        return patientQueryService.getTodayMedicationSchedule(id, timeLabel, date == null ? LocalDate.now() : date);
    }

}
