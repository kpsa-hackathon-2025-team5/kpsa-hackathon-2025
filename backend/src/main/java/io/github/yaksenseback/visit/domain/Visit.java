package io.github.yaksenseback.visit.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.Pharmacist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.yaksenseback.visit.domain.VisitPurpose.REGULAR_CHECKUP;

@Entity
@Table(name = "visit")
@Getter
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private Pharmacist pharmacist;

    /**
     * 예약시간
     */
    @Column(name = "scheduled_start_date_time", nullable = false)
    private LocalDateTime scheduledStartDateTime;

    /**
     * 예약시작시간
     */
    @Column(name = "scheduled_start_date")
    @Deprecated
    private LocalDateTime scheduledStartDate;

    /**
     * 예약종료시간
     */
    @Column(name = "scheduled_end_date")
    @Deprecated
    private LocalDateTime scheduledEndDate;

    /**
     * 예상 소요시간 (분)
     */
    @Column(name = "estimated_duration")
    @Deprecated
    private Integer estimatedDuration;

    /**
     * 실제 시작시간
     */
    @Column(name = "actual_start_date")
    @Deprecated
    private LocalDateTime actualStartDate;

    /**
     * 실제 종료시간
     */
    @Column(name = "actual_end_date")
    @Deprecated
    private LocalDateTime actualEndDate;

    /**
     * 실제 소요시간 (분)
     */
    @Column(name = "actual_duration")
    @Deprecated
    private Long actualDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitStatus status = VisitStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_purpose", nullable = false)
    private VisitPurpose visitPurpose = REGULAR_CHECKUP;

    /**
     * 환자 주소
     */
    @Column(name = "patient_address")
    private String patientAddress;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    /**
     * 응급 연락처
     */
    @Column(name = "emergency_contact")
    private String emergencyContact;

    /**
     *
     */
    private String purposeMemo;

    /**
     * 특별 지시사항
     */
    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;

    /**
     * 결과 지시사항
     */
    @Column(name = "result_instructions", columnDefinition = "TEXT")
    private String resultInstructions;

    @Builder
    public Visit(Patient patient, Pharmacist pharmacist, BigDecimal latitude, BigDecimal longitude,
                 LocalDateTime scheduledStartDateTime,
                 LocalDateTime scheduledStartDate, LocalDateTime scheduledEndDate, Integer estimatedDuration,
                 VisitPurpose visitPurpose, String purposeMemo, String patientAddress, String emergencyContact,
                 String specialInstructions) {
        this.patient = patient;
        this.pharmacist = pharmacist;
        this.scheduledStartDateTime = scheduledStartDateTime;
        this.scheduledStartDate = scheduledStartDate;
        this.scheduledEndDate = scheduledEndDate;
        this.estimatedDuration = estimatedDuration;
        this.visitPurpose = REGULAR_CHECKUP;
        this.purposeMemo = purposeMemo;
        this.patientAddress = patientAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergencyContact = emergencyContact;
        this.specialInstructions = specialInstructions;
    }

    public void approve() {
        if (this.status != VisitStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태가 아니면 수락할 수 없습니다.");
        }
        this.status = VisitStatus.APPROVED;
    }

    public void cancel() {
        // FIXME 취소 조건?
        this.status = VisitStatus.CANCELLED;
    }

    public void start() {
        if (this.status != VisitStatus.APPROVED) {
            throw new IllegalStateException("APPROVED 상태가 아니면 시작할 수 없습니다.");
        }
        this.status = VisitStatus.ONGOING;
        this.actualStartDate = LocalDateTime.now();
    }

    public void complete(LocalDateTime actualEndDate, String resultInstructions) {
        if (this.status != VisitStatus.APPROVED && this.status != VisitStatus.ONGOING) {
            throw new IllegalStateException("APPROVED, ONGOING 상태가 아니면 종료할 수 없습니다.");
        }
        this.status = VisitStatus.COMPLETED;
        this.actualEndDate = actualEndDate;
//        this.actualDuration = Duration.between(this.actualStartDate, this.actualEndDate).toMinutes();
        this.resultInstructions = resultInstructions;
    }

    public void miss() {
        if (this.status != VisitStatus.PENDING && this.status != VisitStatus.APPROVED) {
            throw new IllegalStateException("PENDIND, APPROVED 상태가 아니면 방문을 놓칠 수 없습니다.");
        }
        this.status = VisitStatus.MISSED;
    }

}
