package io.github.yaksenseback.member.application;

import io.github.yaksenseback.member.application.dto.MemberDto;
import io.github.yaksenseback.member.domain.Guardian;
import io.github.yaksenseback.member.domain.GuardianRepository;
import io.github.yaksenseback.member.domain.MemberRepository;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.PatientRepository;
import io.github.yaksenseback.member.domain.Pharmacist;
import io.github.yaksenseback.member.domain.PharmacistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetMemberService {

    private final MemberRepository memberRepository;
    private final GuardianRepository guardianRepository;
    private final PatientRepository patientRepository;
    private final PharmacistRepository pharmacistRepository;

    public Guardian getGuardianById(Long id) {
        return guardianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guardian not found with id: " + id));
    }

    /**
     * 복용자 상세 정보
     */
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    }

    public Pharmacist getPharmacistById(Long id) {
        return pharmacistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pharmacist not found with id: " + id));
    }

    public MemberDto getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberDto::from)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));
    }


}
