package io.github.yaksenseback.visit.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.Pharmacist;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 방문 결과
 */
@Entity
@Table(name = "visit_result")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisitResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visit_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private Visit visit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private Pharmacist pharmacist;

    /**
     * 환자 기저질환
     */
    private String chronicDisease;

    /**
     * 방문 날짜
     */
    private LocalDateTime visitDateTime;

    /**
     * 다음 방문 예정일
     */
    private LocalDate nextVisitDate;

    /**
     * 종합 의견
     */
    private String summary;

    @Builder
    public VisitResult(Visit visit, String chronicDisease, LocalDateTime visitDateTime, LocalDate nextVisitDate, String summary) {
        this.visit = visit;
        this.patient = visit.getPatient();
        this.pharmacist = visit.getPharmacist();
        this.chronicDisease = chronicDisease;
        this.visitDateTime = visitDateTime;
        this.nextVisitDate = nextVisitDate;
        this.summary = summary;
    }

}
