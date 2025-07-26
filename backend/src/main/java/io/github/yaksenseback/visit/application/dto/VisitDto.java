package io.github.yaksenseback.visit.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.Pharmacist;
import io.github.yaksenseback.visit.domain.Visit;
import io.github.yaksenseback.visit.domain.VisitStatus;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.time.LocalDateTime;

@Getter
public class VisitDto {

    private Long id;

    private LocalDateTime scheduledStartDateTime;
    private String purposeMemo;


    @Delegate
    @JsonIgnore
    private Patient patient;

    private Long patientId;
    private String patientName;

    @Delegate
    @JsonIgnore
    private Pharmacist pharmacist;

    private Long pharmacistId;
    private String pharmacistName;
    private VisitStatus visitStatus;

    public VisitDto(Visit visit) {
        this.id = visit.getId();
        this.purposeMemo = visit.getPurposeMemo();
        this.patientName = visit.getPatient().getName();
        this.visitStatus = visit.getStatus();
        this.scheduledStartDateTime = visit.getScheduledStartDateTime();
        this.patient = visit.getPatient();
        this.patientId = visit.getPatient().getId();
        this.pharmacist = visit.getPharmacist();
        this.pharmacistName = visit.getPharmacist().getName();
        this.pharmacistId = visit.getPharmacist().getId();
    }


}
