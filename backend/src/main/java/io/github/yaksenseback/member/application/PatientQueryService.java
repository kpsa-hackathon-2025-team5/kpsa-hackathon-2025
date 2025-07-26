package io.github.yaksenseback.member.application;

import io.github.yaksenseback.medication.domain.MedicationPrescription;
import io.github.yaksenseback.medication.domain.MedicationPrescriptionRepository;
import io.github.yaksenseback.medication.domain.MedicationRecord;
import io.github.yaksenseback.medication.domain.MedicationRecordRepository;
import io.github.yaksenseback.medication.domain.MedicationScheduleRepository;
import io.github.yaksenseback.medication.domain.TimeLabel;
import io.github.yaksenseback.member.application.dto.MedicationScheduleDto;
import io.github.yaksenseback.member.application.dto.PatientDetailDto;
import io.github.yaksenseback.member.domain.Patient;
import io.github.yaksenseback.member.domain.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientQueryService {

    private final PatientRepository patientRepository;
    private final MedicationPrescriptionRepository medicationPrescriptionRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;
    private final MedicationRecordRepository medicationRecordRepository;

    public PatientDetailDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));

        List<MedicationPrescription> prescriptions = medicationPrescriptionRepository.findByPatientId(patient.getId());

        return new PatientDetailDto(patient, prescriptions);
    }

    public List<MedicationScheduleDto> getPatientMedicationSchedule(Long id) {
        return medicationScheduleRepository.findAllByMemberId(id)
                .stream()
                .map(MedicationScheduleDto::new)
                .toList();
    }

    public List<MedicationRecord> getPatientMedicationRecords(Long id) {
        return medicationRecordRepository.findAllByMemberId(id)
                .stream()
                .toList();
    }

    /**
     * 오늘 복용해야할 약 목록
     */
    public List<MedicationScheduleDto> getTodayMedicationSchedule(Long memberId, TimeLabel timeLabel, LocalDate date) {
        return medicationScheduleRepository.findAllMedicationSchedules(memberId, timeLabel, date)
                .stream()
                .map(MedicationScheduleDto::new)
                .toList();
    }

}
