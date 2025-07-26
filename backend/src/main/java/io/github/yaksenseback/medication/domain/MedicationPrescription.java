package io.github.yaksenseback.medication.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
import io.github.yaksenseback.drug.domain.DrugProductInfo;
import io.github.yaksenseback.member.domain.Patient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 복약 처방 (하나의 약물에 대한 전체 처방 정보)
 */
@Entity
@Table(name = "medication_prescription")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicationPrescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_product_info_id", nullable = false)
    @JsonIgnore
    private DrugProductInfo drugProductInfo;

    /**
     * 약 이름
     */
    @Column(name = "drug_name")
    private String drugName;

    /*
     * 처방명 (예: "혈압약 아침저녁 복용")
     */
    @Column(name = "prescription_name", length = 200)
    private String prescriptionName;

    /*
     * 복약 시작일
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /*
     * 복약 종료일
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /*
     * 1회 복용량
     */
    @Column(name = "dosage_per_time", length = 100)
    private String dosagePerTime;

    /*
     * 복용 방법 (식전, 식후, 공복 등)
     */
    @Column(name = "dosage_instruction", length = 200)
    private String dosageInstruction;

    /*
     * 처방 활성화 여부
     */
    @Column(name = "is_active")
    private Boolean isActive = true;

    /*
     * 처방 메모
     */
    @Column(name = "memo", length = 500)
    private String memo;

    /*
     * 복약 시간들
     */
    @OneToMany(mappedBy = "medicationPrescription", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MedicationSchedule> medicationSchedules = new ArrayList<>();

    @Builder
    public MedicationPrescription(Patient patient,
                                  DrugProductInfo drugProductInfo,
                                  String drugName,
                                  String prescriptionName,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  String dosagePerTime,
                                  String dosageInstruction,
                                  String memo,
                                  List<MedicationSchedule> medicationSchedules) {
        Objects.requireNonNull(patient, "환자 정보는 필수입니다.");
        Objects.requireNonNull(drugProductInfo, "약물 정보는 필수입니다.");
        Objects.requireNonNull(startDate, "복약 시작일은 필수입니다.");
        Objects.requireNonNull(dosagePerTime, "1회 복용량은 필수입니다.");
        CollectionUtils.isEmpty(medicationSchedules);

        this.patient = patient;
        this.drugProductInfo = drugProductInfo;
        this.drugName = drugName;
        this.prescriptionName = prescriptionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosagePerTime = dosagePerTime;
        this.dosageInstruction = dosageInstruction;
        this.memo = memo;
        for (MedicationSchedule schedule : medicationSchedules) {
            addMedicationSchedule(schedule);
        }
    }

    public void addMedicationSchedule(MedicationSchedule schedule) {
        schedule.setMedicationPrescription(this);
        schedule.setMemberId(patient.getId());
        this.medicationSchedules.add(schedule);
    }

}
