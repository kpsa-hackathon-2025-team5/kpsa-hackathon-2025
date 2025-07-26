package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRecordRepository extends JpaRepository<MedicationRecord, Long> {
    Optional<MedicationRecord> findByMedicationScheduleId(Long medicationScheduleId);

    List<MedicationRecord> findAllByMemberId(Long memberId);
}
