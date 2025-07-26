package io.github.yaksenseback.visit.domain;

import java.util.List;

public interface VisitCustomRepository {

    List<Visit> findAllVisitsByPatient(Long patientId);

    List<Visit> findAllVisitsByPharmacist(Long pharmacistId);

}
