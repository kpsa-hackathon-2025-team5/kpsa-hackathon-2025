package io.github.yaksenseback.member.application;

import io.github.yaksenseback.member.application.dto.RegisterGuardianRequest;
import io.github.yaksenseback.member.application.dto.RegisterPatientRequest;
import io.github.yaksenseback.member.application.dto.RegisterPharmacistRequest;
import io.github.yaksenseback.member.domain.Guardian;
import io.github.yaksenseback.member.domain.GuardianRepository;
import io.github.yaksenseback.member.domain.MemberRepository;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.PatientRepository;
import io.github.yaksenseback.member.domain.Pharmacist;
import io.github.yaksenseback.member.domain.PharmacistRepository;
import io.github.yaksenseback.member.exception.MemberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterMemberService {

    private final MemberRepository memberRepository;
    private final GuardianRepository guardianRepository;
    private final PatientRepository patientRepository;
    private final PharmacistRepository pharmacistRepository;

    public Long registerGuardian(RegisterGuardianRequest request) {
        validateMember(request.getUsername(), request.getPassword());
        Guardian guardian = Guardian.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .relationship(request.getRelationship())
                .build();

        return guardianRepository.save(guardian).getId();
    }

    public Long registerPatient(RegisterPatientRequest request) {
        validateMember(request.getUsername(), request.getPassword());
        Patient patient = Patient.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .chronicDisease(request.getChronicDisease())
                .medicalHistory(request.getMedicalHistory())
                .build();

        return patientRepository.save(patient).getId();
    }

    public Long registerPharmacist(RegisterPharmacistRequest request) {
        validateMember(request.getUsername(), request.getPassword());
        Pharmacist pharmacist = Pharmacist.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .pharmacyName(request.getPharmacyName())
                .build();

        return pharmacistRepository.save(pharmacist).getId();
    }


    private void validateMember(String username, String password) {
        if (memberRepository.existsByUsername(username)) {
            throw new MemberAlreadyExistsException();
        }
    }

}
