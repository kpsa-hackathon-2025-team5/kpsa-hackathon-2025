package io.github.yaksenseback.medication.domain;

import java.time.LocalTime;

public record RegisterMedicationScheduleRequest(
        LocalTime medicationTime,
        String timeLabel,
        String scheduleMemo) {
}
