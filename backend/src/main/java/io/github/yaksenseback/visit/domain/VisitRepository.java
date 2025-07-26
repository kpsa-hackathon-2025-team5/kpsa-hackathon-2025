package io.github.yaksenseback.visit.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long>, VisitCustomRepository {
}
