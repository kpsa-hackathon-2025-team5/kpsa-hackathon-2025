package io.github.yaksenseback.member.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.jpa.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("PATIENT")
@Getter
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient extends Member {

    /**
     * 생년월일
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * 거주지 주소
     */
    @Column(name = "address")
    private String address;

    /**
     * 병력
     * TODO: 상세 설계 필요
     */
    @Column(name = "medical_history")
    private String medicalHistory;

    /**
     * 기저질환
     */
    @Column(name = "chronic_disease")
    @Convert(converter = StringListConverter.class)
    private List<String> chronicDisease;

    @Builder
    public Patient(String username,
                   String password,
                   String profileImageUrl,
                   String name,
                   LocalDate birthDate,
                   String address,
                   String medicalHistory,
                   List<String> chronicDisease) {
        super(username, password, profileImageUrl, name, MemberType.PATIENT);
        this.birthDate = birthDate;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.chronicDisease = chronicDisease;
    }

}
