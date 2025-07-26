package io.github.yaksenseback.visit.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
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
import lombok.Setter;

/**
 * 방문 복약 스케줄
 * - 방문 후 환자의 복약 스케줄을 설정
 */
@Entity
@Table(name = "visit_medication_schedule")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisitMedicationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_result_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private VisitResult visitResult;

    /*
     * 시간대 라벨 (아침, 점심, 저녁, 취침전 등)
     */
    @Column(name = "time_label", length = 50)
    private String timeLabel;

    /**
     * 식전/식후
     */
    @Column(name = "before_after_meal", length = 20)
    private String beforeAfterMeal;

    /**
     * 복용량
     */
    @Column(name = "dosage", nullable = false)
    private String dosage;


}
