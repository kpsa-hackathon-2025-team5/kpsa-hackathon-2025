package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicationRecordRepository extends JpaRepository<MedicationRecord, Long>, MedicationRecordCustomRepository {

    Optional<MedicationRecord> findByMedicationScheduleId(Long medicationScheduleId);

    Optional<MedicationRecord> findByMedicationScheduleIdAndScheduledAt(Long medicationScheduleId, LocalDate scheduledAt);

    List<MedicationRecord> findAllByMemberId(Long memberId);
}
