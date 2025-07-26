package io.github.yaksenseback.medication.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_side_effect_record")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DailySideEffectRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; // 환자 ID

    /**
     * 이상 반응 종류
     */
    @Enumerated(EnumType.STRING)
    private SideEffectType sideEffectType;

    /**
     * 이상 반응 정도
     */
    @Enumerated(EnumType.STRING)
    private SideEffectLevel sideEffectLevel;

    /**
     * 메모
     */
    private String sideEffectNote;


    public DailySideEffectRecord(Long memberId, SideEffectType sideEffectType, SideEffectLevel sideEffectLevel, String sideEffectNote) {
        this.memberId = memberId;
        this.sideEffectType = sideEffectType;
        this.sideEffectLevel = sideEffectLevel;
        this.sideEffectNote = sideEffectNote;
    }

}
