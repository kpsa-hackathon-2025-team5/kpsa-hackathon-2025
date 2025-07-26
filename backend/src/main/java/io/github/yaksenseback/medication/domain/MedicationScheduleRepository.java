package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Long>, MedicationScheduleCustomRepository {
    List<MedicationSchedule> findAllByMemberId(Long memberId);
}
