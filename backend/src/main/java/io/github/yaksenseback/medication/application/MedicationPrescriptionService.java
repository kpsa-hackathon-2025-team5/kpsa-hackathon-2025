package io.github.yaksenseback.medication.application;

import io.github.yaksenseback.drug.domain.DrugProductInfoRepository;
import io.github.yaksenseback.medication.application.dto.RegisterMedicationPrescriptionRequest;
import io.github.yaksenseback.medication.domain.MedicationPrescription;
import io.github.yaksenseback.medication.domain.MedicationPrescriptionRepository;
import io.github.yaksenseback.medication.domain.MedicationSchedule;
import io.github.yaksenseback.member.domain.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationPrescriptionService {

    private final MedicationPrescriptionRepository medicationPrescriptionRepository;
    private final PatientRepository patientRepository;
    private final DrugProductInfoRepository drugProductInfoRepository;

    public Long createPrescription(RegisterMedicationPrescriptionRequest dto) {
        MedicationPrescription prescription = MedicationPrescription.builder()
                .patient(patientRepository.findById(dto.patientId())
                        .orElseThrow(() -> new IllegalArgumentException("환자를 찾을 수 없습니다.")))
                .drugProductInfo(drugProductInfoRepository.findById(dto.drugProductInfoId())
                        .orElseThrow(() -> new IllegalArgumentException("약물을 찾을 수 없습니다.")))
                .prescriptionName(dto.prescriptionName())
                .drugName(dto.drugName())
                .dosageInstruction(dto.dosageInstruction())
                .dosagePerTime(dto.dosagePerTime())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .memo(dto.memo())
                .medicationSchedules(dto.schedules().stream()
                        .map(schedule -> new MedicationSchedule(schedule.medicationTime(), schedule.timeLabel(), schedule.scheduleMemo()))
                        .toList())
                .build();

        medicationPrescriptionRepository.save(prescription);
        return prescription.getId();
    }

    public List<MedicationPrescription> getMedicationPrescriptions(Long memberId) {
        return medicationPrescriptionRepository.findByPatientId(memberId);
    }

}
