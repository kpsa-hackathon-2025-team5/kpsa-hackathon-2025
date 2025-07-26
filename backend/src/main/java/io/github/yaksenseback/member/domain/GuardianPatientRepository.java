package io.github.yaksenseback.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuardianPatientRepository extends JpaRepository<GuardianPatient, Long> {
    List<GuardianPatient> findAllByGuardianId(Long guardianId);
}
