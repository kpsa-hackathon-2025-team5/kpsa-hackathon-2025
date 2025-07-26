package io.github.yaksenseback.medication.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSlotComplianceResponse {
    private String timeSlot;
    private int requiredCount;
    private int takenCount;
    private int result;
}
