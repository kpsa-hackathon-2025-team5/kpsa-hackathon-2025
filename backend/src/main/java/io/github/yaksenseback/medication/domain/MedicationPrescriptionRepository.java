package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationPrescriptionRepository extends JpaRepository<MedicationPrescription, Long> {
    List<MedicationPrescription> findByPatientId(Long memberId);
}
