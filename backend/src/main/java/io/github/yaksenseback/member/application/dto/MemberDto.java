package io.github.yaksenseback.member.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.yaksenseback.member.domain.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private Long id;
    private String username;
    private String name;
    private MemberType memberType;
    private MemberStatus status;

    // Guardian
    private String relationship;

    // Patient
    private LocalDate birthDate;
    private String address;
    private String medicalHistory;

    // Pharmacist
    private String licenseNumber;
    private String pharmacyName;


    public static MemberDto from(Member member) {
        MemberDto response = new MemberDto();
        response.id = member.getId();
        response.username = member.getUsername();
        response.name = member.getName();
        response.memberType = member.getMemberType();
        response.status = member.getStatus();

        if (member instanceof Patient patient) {
            response.birthDate = patient.getBirthDate();
            response.address = patient.getAddress();
            response.medicalHistory = patient.getMedicalHistory();
        } else if (member instanceof Guardian guardian) {
            response.relationship = guardian.getRelationship();
        } else if (member instanceof Pharmacist pharmacist) {
            response.licenseNumber = pharmacist.getLicenseNumber();
            response.pharmacyName = pharmacist.getPharmacyName();
        } else {
            throw new IllegalStateException("Unexpected member type: " + member.getClass().getSimpleName());
        }

        return response;
    }
}
