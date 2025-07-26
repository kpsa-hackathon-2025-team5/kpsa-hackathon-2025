package io.github.yaksenseback.medication.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 환자가 남기는 복약 기록
 * - 스케줄링으로 알림 발생 시 생성된다.
 * - 생성 시 복약 여부는 false로 설정되며 복약 기록을 남길 때 true가 된다.
 */
@Entity
@Table(name = "medication_record")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicationRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;


    @Column(name = "medication_schedule_id", insertable = false, updatable = false)
    private Long medicationScheduleId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medication_schedule_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private MedicationSchedule medicationSchedule;

    /**
     * 복약 여부
     */
    private boolean isTaken = false;

    private LocalDateTime takenAt; //FIXME: 복약 기록이 남겨진 시간

    /**
     * 이상 반응 종류
     */
    @Enumerated(EnumType.STRING)
    @Deprecated
    private SideEffectType sideEffectType;

    /**
     * 이상 반응 정도
     */
    @Enumerated(EnumType.STRING)
    @Deprecated
    private SideEffectLevel sideEffectLevel;

    /**
     * 메모
     */
    @Deprecated
    private String sideEffectNote;


    public MedicationRecord(Long memberId, MedicationSchedule medicationSchedule) {
        this.memberId = memberId;
        this.medicationSchedule = medicationSchedule;
    }


    public void taken(SideEffectType sideEffectType, SideEffectLevel sideEffectLevel, String sideEffectNote, LocalDateTime takenAt) {
//        this.sideEffectType = sideEffectType;
//        this.sideEffectNote = sideEffectNote;
//        this.sideEffectLevel = sideEffectLevel;
        this.isTaken = true;
        this.takenAt = takenAt;
    }

}
