package io.github.yaksenseback.member.application.dto;

import io.github.yaksenseback.medication.domain.MedicationSchedule;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MedicationScheduleDto {

//    @Delegate
//    @JsonIgnore
//    private MedicationSchedule medicationSchedule;

    private Long id;
    private Long memberId;
    private LocalTime medicationTime;
    private String timeLabel;
    private Boolean isActive;
    private LocalDateTime lastTakenAt;
    private String scheduleMemo;

    // private MedicationPrescriptionDto medicationPrescription;
    private Long medicationPrescriptionId;
    private String drugName;
    private String prescriptionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dosagePerTime;
    private String dosageInstruction;
    private String memo;


    public MedicationScheduleDto(MedicationSchedule medicationSchedule) {
        this.id = medicationSchedule.getId();
        this.memberId = medicationSchedule.getMemberId();
        this.medicationTime = medicationSchedule.getMedicationTime();
        this.timeLabel = medicationSchedule.getTimeLabel();
        this.isActive = medicationSchedule.getIsActive();
        this.lastTakenAt = medicationSchedule.getLastTakenAt();
        this.scheduleMemo = medicationSchedule.getScheduleMemo();


        var medicationPrescription = medicationSchedule.getMedicationPrescription();
        this.medicationPrescriptionId = medicationPrescription.getId();
        this.drugName = medicationPrescription.getDrugName();
        this.prescriptionName = medicationPrescription.getPrescriptionName();
        this.startDate = medicationPrescription.getStartDate();
        this.endDate = medicationPrescription.getEndDate();
        this.dosagePerTime = medicationPrescription.getDosagePerTime();
        this.dosageInstruction = medicationPrescription.getDosageInstruction();
        this.memo = medicationPrescription.getMemo();
    }

/*    @Getter
    public static class MedicationPrescriptionDto {

        private String drugName;
        private String prescriptionName;
        private LocalDate startDate;
        private LocalDate endDate;
        private String dosagePerTime;
        private String dosageInstruction;
        private String memo;
        public MedicationPrescriptionDto(MedicationPrescription medicationPrescription) {
            this.drugName = medicationPrescription.getDrugName();
            this.prescriptionName = medicationPrescription.getPrescriptionName();
            this.startDate = medicationPrescription.getStartDate();
            this.endDate = medicationPrescription.getEndDate();
            this.dosagePerTime = medicationPrescription.getDosagePerTime();
            this.dosageInstruction = medicationPrescription.getDosageInstruction();
            this.memo = medicationPrescription.getMemo();
        }
    }*/

}
