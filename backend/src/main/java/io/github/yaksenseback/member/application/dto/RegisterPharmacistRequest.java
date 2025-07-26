package io.github.yaksenseback.member.application.dto;

import lombok.Getter;

@Getter
public class RegisterPharmacistRequest extends RegisterMemberRequest {

    private final String licenseNumber;
    private final String pharmacyName;

    public RegisterPharmacistRequest(String username, String password, String name, String profileImageUrl, String pharmacyName, String licenseNumber) {
        super(username, password, name, profileImageUrl);
        this.pharmacyName = pharmacyName;
        this.licenseNumber = licenseNumber;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

}
