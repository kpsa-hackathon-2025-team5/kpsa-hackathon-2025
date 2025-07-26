package io.github.yaksenseback.medication.ui;

import io.github.yaksenseback.medication.application.CreateDailySideEffectRecordService;
import io.github.yaksenseback.medication.application.MedicationRecordService;
import io.github.yaksenseback.medication.application.dto.CreateDailySideEffectRecordRequest;
import io.github.yaksenseback.medication.domain.DailySideEffectRecord;
import io.github.yaksenseback.medication.domain.MedicationRecord;
import io.github.yaksenseback.medication.domain.SideEffectLevel;
import io.github.yaksenseback.medication.domain.SideEffectType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications/records")
@RequiredArgsConstructor
public class MedicationRecordRestController {


    private final MedicationRecordService medicationRecordService;
    private final CreateDailySideEffectRecordService createDailySideEffectRecordService;


    @PostMapping
    public MedicationRecord createMedicationRecord(@RequestBody CreateMedicationRecordRequest medicationRecord) {
        return medicationRecordService.createMedicationRecord(
                medicationRecord.memberId(),
                medicationRecord.medicationScheduleId()
        );
    }

    @PostMapping("/taken")
    public void taken(@RequestBody TakenMedicationRecordRequest request) {
        medicationRecordService.taken(
                request.medicationScheduleIds(),
                request.sideEffectType(),
                request.sideEffectLevel(),
                request.sideEffectNote()
        );
    }

    @PostMapping("/sideEffects")
    public DailySideEffectRecord createDailySideEffectRecord(@RequestBody CreateDailySideEffectRecordRequest request) {
        return createDailySideEffectRecordService.createDailySideEffectRecord(request);
    }

    public record CreateMedicationRecordRequest(Long memberId, Long medicationScheduleId) {
    }

    public record TakenMedicationRecordRequest(List<Long> medicationScheduleIds,
                                               SideEffectType sideEffectType,
                                               SideEffectLevel sideEffectLevel,
                                               String sideEffectNote) {
    }
}
