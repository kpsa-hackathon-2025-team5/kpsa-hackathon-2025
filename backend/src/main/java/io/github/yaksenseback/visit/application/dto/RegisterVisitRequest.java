package io.github.yaksenseback.visit.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.yaksenseback.visit.domain.VisitPurpose;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class RegisterVisitRequest {
    private final Long patientId;
    private final Long pharmacistId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime scheduledStartDateTime;
    @Deprecated
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime scheduledStartDate;
    @Deprecated
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime scheduledEndDate;
    private final Integer estimatedDuration;
    private final VisitPurpose visitPurpose;
    private final String patientAddress;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String emergencyContact;
    private final String specialInstructions;

    public RegisterVisitRequest(Long patientId, Long pharmacistId, LocalDateTime scheduledStartDateTime, LocalDateTime scheduledStartDate,
                                LocalDateTime scheduledEndDate, Integer estimatedDuration, VisitPurpose visitPurpose,
                                String patientAddress, BigDecimal latitude, BigDecimal longitude, String emergencyContact,
                                String specialInstructions) {
        this.patientId = patientId;
        this.pharmacistId = pharmacistId;
        this.scheduledStartDateTime = scheduledStartDateTime;
        this.scheduledStartDate = scheduledStartDate;
        this.scheduledEndDate = scheduledEndDate;
        this.estimatedDuration = estimatedDuration;
        this.visitPurpose = visitPurpose;
        this.patientAddress = patientAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergencyContact = emergencyContact;
        this.specialInstructions = specialInstructions;
    }
}
