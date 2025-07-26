package io.github.yaksenseback.visit.application;

import io.github.yaksenseback.common.exception.ResourceNotFoundException;
import io.github.yaksenseback.visit.application.dto.VisitDto;
import io.github.yaksenseback.visit.application.dto.VisitSearchRequest;
import io.github.yaksenseback.visit.domain.Visit;
import io.github.yaksenseback.visit.domain.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitQueryService {

    private final VisitRepository visitRepository;


    public Visit getVisit(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new ResourceNotFoundException("방문 정보를 찾을 수 없습니다: " + visitId));
    }

    public List<VisitDto> getAllVisitsByPatient(Long patientId, VisitSearchRequest request) {
        return visitRepository.findAllVisitsByPatient(patientId).stream()
                .map(VisitDto::new)
                .toList();
    }

    public List<Visit> getAllVisitsByPharmacist(Long pharmacistId) {
        return visitRepository.findAllVisitsByPharmacist(pharmacistId);
    }

}
