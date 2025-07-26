package io.github.yaksenseback.visit.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.Pharmacist;
import io.github.yaksenseback.visit.domain.Visit;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.time.LocalDateTime;

@Getter
public class VisitDto {

    private Long id;

    private LocalDateTime scheduledStartDateTime;

    @Delegate
    @JsonIgnore
    private Patient patient;

    private Long patientId;

    @Delegate
    @JsonIgnore
    private Pharmacist pharmacist;

    private Long pharmacistId;

    public VisitDto(Visit visit) {
        this.id = visit.getId();
        this.scheduledStartDateTime = visit.getScheduledStartDateTime();
        this.patient = visit.getPatient();
        this.patientId = visit.getPatient().getId();
        this.pharmacist = visit.getPharmacist();
        this.pharmacistId = visit.getPharmacist().getId();
    }


}
