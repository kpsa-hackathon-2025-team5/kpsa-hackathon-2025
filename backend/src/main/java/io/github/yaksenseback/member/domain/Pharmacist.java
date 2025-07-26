package io.github.yaksenseback.member.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@DiscriminatorValue("PHARMACIST")
@Getter
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pharmacist extends Member {

    /**
     * 약사 면허번호
     */
    @Column(name = "license_number")
    private String licenseNumber;

    /**
     * 소속 약국명
     */
    @Column(name = "pharmacy_name")
    private String pharmacyName;

    @Builder
    public Pharmacist(String username,
                      String password,
                      String profileImageUrl,
                      String name,
                      String licenseNumber,
                      String pharmacyName) {
        super(username, password, profileImageUrl, name, MemberType.PHARMACIST);
        this.licenseNumber = Objects.requireNonNull(licenseNumber);
        this.pharmacyName = pharmacyName;
    }

}
