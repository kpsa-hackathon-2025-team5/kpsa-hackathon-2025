package io.github.yaksenseback.medication.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationNotificationRepository extends JpaRepository<MedicationNotification, Long> {
}
