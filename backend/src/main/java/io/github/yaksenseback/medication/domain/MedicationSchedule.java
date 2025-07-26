package io.github.yaksenseback.medication.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "medication_schedule")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicationSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medication_prescription_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    @JsonIgnore
    private MedicationPrescription medicationPrescription;

    /**
     * 환자 ID
     * - 알림 기능을 위해 추가
     */
    @Column(name = "member_id", nullable = false, updatable = false)
    @Setter(AccessLevel.PACKAGE)
    private Long memberId;

    /*
     * 복약 시간 (hh:mm 형식)
     */
    @Column(name = "medication_time", nullable = false)
    private LocalTime medicationTime;

    /*
     * 시간대 라벨 (아침, 점심, 저녁, 취침전 등)
     */
    @Column(name = "time_label", length = 50)
    @Enumerated(EnumType.STRING)
    private TimeLabel timeLabel;

    /*
     * 스케줄 활성화 여부
     */
    @Column(name = "is_active", nullable = false)
    @Setter
    private Boolean isActive = true;

    /*
     * 마지막 복용 확인 시간
     */
    @Column(name = "last_taken_at")
    private LocalDateTime lastTakenAt;

    /*
     * 스케줄별 메모 (예: "식후 30분", "물 많이 마시기")
     */
    @Column(name = "schedule_memo", length = 200)
    private String scheduleMemo;

    public MedicationSchedule(LocalTime medicationTime, TimeLabel timeLabel, String scheduleMemo) {
        this.medicationTime = medicationTime;
        this.timeLabel = timeLabel;
        this.scheduleMemo = scheduleMemo;
    }

    public void check(LocalDateTime takenAt) {
        this.lastTakenAt = takenAt;
    }

}
