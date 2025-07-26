package io.github.yaksenseback.medication.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medications/records")
@RequiredArgsConstructor
public class MedicationRecordRestController {


    private final MedicationRecordService medicationRecordService;
    private final CreateDailySideEffectRecordService createDailySideEffectRecordService;


    @PostMapping
    public MedicationRecord createMedicationRecord(@RequestBody CreateMedicationRecordRequest request) {
        return medicationRecordService.createMedicationRecord(
                request.memberId(),
                request.scheduledAt(),
                request.medicationScheduleId()
        );
    }

    @PostMapping("/taken")
    public void taken(@RequestBody TakenMedicationRecordRequest request) {
        medicationRecordService.taken(
                request.medicationScheduleIds(),
                request.scheduledAt(),
                request.sideEffectType(),
                request.sideEffectLevel(),
                request.sideEffectNote()
        );
    }

    @PostMapping("/sideEffects")
    public DailySideEffectRecord createDailySideEffectRecord(@RequestBody CreateDailySideEffectRecordRequest request) {
        return createDailySideEffectRecordService.createDailySideEffectRecord(request);
    }

    public record CreateMedicationRecordRequest(
            Long memberId,
            Long medicationScheduleId,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
            LocalDate scheduledAt) {
    }

    public record TakenMedicationRecordRequest(List<Long> medicationScheduleIds,
                                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                               LocalDate scheduledAt,
                                               SideEffectType sideEffectType,
                                               SideEffectLevel sideEffectLevel,
                                               String sideEffectNote) {
    }
}
