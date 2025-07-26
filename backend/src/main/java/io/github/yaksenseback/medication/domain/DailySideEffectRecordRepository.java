package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySideEffectRecordRepository extends JpaRepository<DailySideEffectRecord, Long> {
}
