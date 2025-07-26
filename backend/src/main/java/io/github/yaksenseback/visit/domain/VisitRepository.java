package io.github.yaksenseback.visit.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long>, VisitCustomRepository {

    Optional<Visit> findAllByPatientIdAndPharmacistIdAndScheduledStartDateTimeBetween(Long patientId, Long pharmacist, LocalDateTime scheduledStartDateTimeAfter, LocalDateTime scheduledStartDateTimeBefore);

}
