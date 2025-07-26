package io.github.yaksenseback.medication.ui;

import io.github.yaksenseback.medication.application.MedicationRecordService;
import io.github.yaksenseback.medication.domain.MedicationRecord;
import io.github.yaksenseback.medication.domain.SideEffectLevel;
import io.github.yaksenseback.medication.domain.SideEffectType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/medications/records")
@RequiredArgsConstructor
public class MedicationRecordRestController {


    private final MedicationRecordService medicationRecordService;


    @PostMapping
    public MedicationRecord createMedicationRecord(@RequestBody CreateMedicationRecordRequest medicationRecord) {
        return medicationRecordService.createMedicationRecord(
                medicationRecord.memberId(),
                medicationRecord.medicationScheduleId()
        );
    }

    @PostMapping("/taken")
    public MedicationRecord taken(@RequestBody TakenMedicationRecordRequest request) {
        return medicationRecordService.taken(
                request.medicationScheduleId(),
                request.sideEffectType(),
                request.sideEffectLevel(),
                request.sideEffectNote()
        );
    }


    public record CreateMedicationRecordRequest(Long memberId, Long medicationScheduleId) {
    }

    public record TakenMedicationRecordRequest(Long medicationScheduleId,
                                               SideEffectType sideEffectType,
                                               SideEffectLevel sideEffectLevel,
                                               String sideEffectNote) {
    }
}
