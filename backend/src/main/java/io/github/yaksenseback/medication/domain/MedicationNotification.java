package io.github.yaksenseback.medication.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.drug.domain.DrugProductInfo;
import io.github.yaksenseback.member.domain.Patient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 환자 복약 알림
 */
@Deprecated
@Entity
@Table(name = "medication_notification")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicationNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_product_info_id", nullable = false)
    private DrugProductInfo drugProductInfo;

    /*
     * 복약 시작일
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /*
     * 복약 종료일
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /*
     * 복약 시간 (시:분)
     */
    @Column(name = "medication_time", nullable = false)
    private LocalTime medicationTime;

    /*
     * 복용량
     */
    @Column(name = "dosage", length = 100)
    private String dosage;

    /*
     * 복용 빈도 (1일 1회, 1일 2회 등)
     */
    @Column(name = "frequency", length = 50)
    private String frequency;

    /*
     * 복용 방법 (식전, 식후, 공복 등)
     */
    @Column(name = "dosage_instruction", length = 200)
    private String dosageInstruction;

    /*
     * 알림 활성화 여부
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /*
     * 마지막 복용 확인 시간
     */
    @Column(name = "last_taken_at")
    private LocalDateTime lastTakenAt;

    /*
     * 메모
     */
    @Column(name = "memo", length = 500)
    private String memo;


    // 생성자
    public MedicationNotification(Patient patient, DrugProductInfo drugProductInfo,
                                  LocalDate startDate, LocalTime medicationTime) {
        this.patient = patient;
        this.drugProductInfo = drugProductInfo;
        this.startDate = startDate;
        this.medicationTime = medicationTime;
    }

    public MedicationNotification(Patient patient, DrugProductInfo drugProductInfo,
                                  LocalDate startDate, LocalDate endDate, LocalTime medicationTime,
                                  String dosage, String frequency, String dosageInstruction) {
        this.patient = patient;
        this.drugProductInfo = drugProductInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicationTime = medicationTime;
        this.dosage = dosage;
        this.frequency = frequency;
        this.dosageInstruction = dosageInstruction;
    }

}
