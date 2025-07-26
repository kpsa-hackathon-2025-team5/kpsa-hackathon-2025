package io.github.yaksenseback.member.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guardian_patient")
@Getter
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuardianPatient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guardianId; // 보호자 ID

    private Long patientId; // 환자 ID


    public GuardianPatient(Long guardianId, Long patientId) {
        this.guardianId = guardianId;
        this.patientId = patientId;
    }

}
