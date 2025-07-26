package io.github.yaksenseback.member.application.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RegisterPatientRequest extends RegisterMemberRequest {

    private final LocalDate birthDate;
    private final String address;
    private final String medicalHistory;
    private final List<String> chronicDisease;

    public RegisterPatientRequest(String username, String password, String name, String profileImageUrl, LocalDate birthDate, String address, String medicalHistory, List<String> chronicDisease) {
        super(username, password, name, profileImageUrl);
        this.birthDate = birthDate;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.chronicDisease = chronicDisease;
    }

}
