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

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationRecordService {

    private final MedicationRecordRepository medicationRecordRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;

    public MedicationRecord createMedicationRecord(Long memberId, Long medicationScheduleId) {
        var medicationSchedule = medicationScheduleRepository.findById(medicationScheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication schedule not found with id: " + medicationScheduleId));

        return medicationRecordRepository.save(new MedicationRecord(memberId, medicationSchedule));
    }

    public MedicationRecord taken(Long medicationScheduleId, SideEffectType sideEffectType, SideEffectLevel sideEffectLevel, String sideEffectNote) {
        var medicationRecord = medicationRecordRepository.findByMedicationScheduleId(medicationScheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication record not found with medication schedule id: " + medicationScheduleId));

        medicationRecord.taken(sideEffectType, sideEffectLevel, sideEffectNote, LocalDateTime.now());

        return medicationRecordRepository.save(medicationRecord);
    }

}
