package io.github.yaksenseback.medication.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyComplianceResponse {
    private String date;
    private int result;
}
