package io.github.yaksenseback.medication.application;

import io.github.yaksenseback.common.exception.ResourceNotFoundException;
import io.github.yaksenseback.medication.domain.MedicationRecord;
import io.github.yaksenseback.medication.domain.MedicationRecordRepository;
import io.github.yaksenseback.medication.domain.MedicationScheduleRepository;
import io.github.yaksenseback.medication.domain.SideEffectLevel;
import io.github.yaksenseback.medication.domain.SideEffectType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationRecordService {

    private final MedicationRecordRepository medicationRecordRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;

    /**
     * 원래는 일자를 변경할 수 없다. 개발용
     */
    public MedicationRecord createMedicationRecord(Long memberId, LocalDate scheduledAt, Long medicationScheduleId) {
        var medicationSchedule = medicationScheduleRepository.findById(medicationScheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication schedule not found with id: " + medicationScheduleId));

        return medicationRecordRepository.save(new MedicationRecord(memberId, scheduledAt, medicationSchedule));
    }


    public MedicationRecord createMedicationRecord(Long memberId, Long medicationScheduleId) {
        return createMedicationRecord(memberId, LocalDate.now(), medicationScheduleId);
    }

    public void taken(List<Long> medicationScheduleIds, LocalDate scheduledAt, SideEffectType sideEffectType, SideEffectLevel sideEffectLevel, String sideEffectNote) {
        medicationScheduleRepository.findAllById(medicationScheduleIds).forEach(
                medicationSchedule -> {
                    var medicationRecord = medicationRecordRepository.findByMedicationScheduleIdAndScheduledAt(medicationSchedule.getId(), scheduledAt)
                            .orElseThrow(() -> new ResourceNotFoundException("Medication record not found with medication schedule id: " + medicationSchedule.getId()));
                    medicationRecord.taken(sideEffectType, sideEffectLevel, sideEffectNote, LocalDateTime.now());
                });
    }

}
