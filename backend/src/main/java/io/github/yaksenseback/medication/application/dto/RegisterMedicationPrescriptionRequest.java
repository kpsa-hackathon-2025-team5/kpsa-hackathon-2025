package io.github.yaksenseback.medication.application.dto;

import io.github.yaksenseback.medication.domain.RegisterMedicationScheduleRequest;

import java.time.LocalDate;
import java.util.List;

public record RegisterMedicationPrescriptionRequest(
        Long patientId,
        Long drugProductInfoId,
        String drugName,
        String prescriptionName,
        LocalDate startDate, LocalDate endDate,
        String dosagePerTime,
        String dosageInstruction,
        // FIXME: 칼럼 추가
        String memo,
        List<RegisterMedicationScheduleRequest> schedules
) {
}
