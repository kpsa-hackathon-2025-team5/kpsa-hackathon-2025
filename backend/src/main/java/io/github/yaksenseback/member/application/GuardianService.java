package io.github.yaksenseback.member.application;

import io.github.yaksenseback.common.exception.ResourceNotFoundException;
import io.github.yaksenseback.member.domain.Guardian;
import io.github.yaksenseback.member.domain.GuardianPatient;
import io.github.yaksenseback.member.domain.GuardianPatientRepository;
import io.github.yaksenseback.member.domain.GuardianRepository;
import io.github.yaksenseback.member.domain.PatientRepository;
import io.github.yaksenseback.member.ui.dto.PatientListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardianService {

    private final PatientRepository patientRepository;
    private final GuardianRepository guardianRepository;
    private final GuardianPatientRepository guardianPatientRepository;


    public Guardian addPatientToGuardian(Long guardianId, Long patientId) {
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id: " + guardianId));
        guardianPatientRepository.save(new GuardianPatient(guardianId, patientId));
        return guardian;
    }

    public List<PatientListDto> getPatientsByGuardian(Long guardianId) {
        List<GuardianPatient> gp = guardianPatientRepository.findAllByGuardianId(guardianId);

        return gp.stream()
                .map(gpItem -> patientRepository.findById(gpItem.getPatientId())
                        .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + gpItem.getPatientId())))
                .map(PatientListDto::new)
                .toList();
    }

}
