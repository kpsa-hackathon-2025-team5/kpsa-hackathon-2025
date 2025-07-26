package io.github.yaksenseback.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yaksenseback.medication.domain.MedicationPrescription;
import io.github.yaksenseback.member.domain.Patient;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PatientDetailDto {

    @Delegate
    @JsonIgnore
    private Patient patient;
    private List<MedicationPrescriptionDto> prescriptions;


    public PatientDetailDto(Patient patient, List<MedicationPrescription> prescriptions) {
        this.patient = patient;
        this.prescriptions = prescriptions.stream()
                .map(MedicationPrescriptionDto::new)
                .toList();
    }

    @Getter
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

    }

}
