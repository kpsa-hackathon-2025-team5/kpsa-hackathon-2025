package io.github.yaksenseback.visit.application;

import io.github.yaksenseback.common.exception.ApplicationException;
import io.github.yaksenseback.member.domain.PatientRepository;
import io.github.yaksenseback.member.domain.PharmacistRepository;
import io.github.yaksenseback.visit.application.dto.CompleteVisitRequest;
import io.github.yaksenseback.visit.application.dto.RegisterVisitRequest;
import io.github.yaksenseback.visit.application.dto.RegisterVisitResultRequest;
import io.github.yaksenseback.visit.domain.Visit;
import io.github.yaksenseback.visit.domain.VisitRepository;
import io.github.yaksenseback.visit.domain.VisitResult;
import io.github.yaksenseback.visit.domain.VisitResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final PharmacistRepository pharmacistRepository;
    private final VisitResultRepository visitResultRepository;

    public Long registerVisit(RegisterVisitRequest request) {

        visitRepository.findAllByPatientIdAndPharmacistIdAndScheduledStartDateTimeBetween(
                request.getPatientId(),
                request.getPharmacistId(),
                request.getScheduledStartDateTime().toLocalDate().atStartOfDay(),
                request.getScheduledStartDateTime().toLocalDate().atTime(23, 59, 59)
        ).ifPresent(existingVisit -> {
            throw new ApplicationException("이미 예약된 방문이 있습니다: " + existingVisit.getId());
        });

        Visit visit = Visit.builder()
                .patient(patientRepository.getReferenceById(request.getPatientId()))
                .pharmacist(pharmacistRepository.getReferenceById(request.getPharmacistId()))
                .scheduledStartDateTime(request.getScheduledStartDateTime())
                .scheduledStartDate(request.getScheduledStartDate())
                .scheduledEndDate(request.getScheduledEndDate())
                .estimatedDuration(request.getEstimatedDuration())
                .visitPurpose(request.getVisitPurpose())
                .purposeMemo(request.getPurposeMemo())
                .patientAddress(request.getPatientAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .emergencyContact(request.getEmergencyContact())
                .specialInstructions(request.getSpecialInstructions())
                .build();

        return visitRepository.save(visit).getId();
    }

    public Visit approveVisit(Long visitId) {
        Visit visit = findVisit(visitId);
        visit.approve();
        visitRepository.save(visit);
        return visit;
    }

    public Visit cancelVisit(Long visitId) {
        Visit visit = findVisit(visitId);
        visit.cancel();
        visitRepository.save(visit);
        return visit;
    }

    // FIXME 완료 처리하는 주체는? 환자? 약사?
    public Visit startVisit(Long visitId) {
        Visit visit = findVisit(visitId);
        visit.start();
        visitRepository.save(visit);
        return visit;
    }

    // FIXME 완료 처리하는 주체는? 환자? 약사?
    public Visit completeVisit(Long visitId, CompleteVisitRequest request) {
        Visit visit = findVisit(visitId);
        visit.complete(
                request.getEndDate(),
                request.getResultInstructions()
        );
        visitRepository.save(visit);
        return visit;
    }

    // FIXME 처리하는 주체는? 환자? 약사?
    public Visit missVisit(Long visitId) {
        Visit visit = findVisit(visitId);
        visit.miss();
        visitRepository.save(visit);
        return visit;
    }

    public VisitResult registerResult(Long visitId, RegisterVisitResultRequest request) {
        Visit visit = findVisit(visitId);
        return visitResultRepository.save(VisitResult.builder()
                .visit(visit)
                .chronicDisease(request.chronicDisease())
                .visitDateTime(request.visitDateTime())
                .nextVisitDate(request.nextVisitDate())
                .summary(request.summary())
                .build()
        );
    }


    private Visit findVisit(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new ApplicationException("방문 정보를 찾을 수 없습니다: " + visitId));
    }

}
