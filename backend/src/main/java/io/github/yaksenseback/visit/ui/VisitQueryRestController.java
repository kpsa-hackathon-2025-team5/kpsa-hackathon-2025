package io.github.yaksenseback.visit.ui;

import io.github.yaksenseback.visit.application.VisitQueryService;
import io.github.yaksenseback.visit.application.dto.VisitDto;
import io.github.yaksenseback.visit.application.dto.VisitSearchRequest;
import io.github.yaksenseback.visit.domain.Visit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitQueryRestController {

    private final VisitQueryService visitQueryService;


    @GetMapping("/{visitId}")
    Visit getVisit(@PathVariable Long visitId) {
        return visitQueryService.getVisit(visitId);
    }

    @GetMapping("/patients/{patientId}")
    public List<VisitDto> getAllVisitsByPatient(@PathVariable Long patientId,
                                                @ModelAttribute VisitSearchRequest request) {
        return visitQueryService.getAllVisitsByPatient(patientId, request);
    }

    @GetMapping("/pharmacists/{pharmacistId}")
    public List<Visit> getAllVisitsByPharmacist(@PathVariable Long pharmacistId) {
        return visitQueryService.getAllVisitsByPharmacist(pharmacistId);
    }

}
