package io.github.yaksenseback.visit.ui;

import io.github.yaksenseback.visit.application.VisitService;
import io.github.yaksenseback.visit.application.dto.CompleteVisitRequest;
import io.github.yaksenseback.visit.application.dto.RegisterVisitRequest;
import io.github.yaksenseback.visit.application.dto.RegisterVisitResultRequest;
import io.github.yaksenseback.visit.domain.Visit;
import io.github.yaksenseback.visit.domain.VisitResult;
import io.github.yaksenseback.visit.ui.dto.RegisterVisitResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitRestController {

    private final VisitService visitService;


    @PostMapping
    public RegisterVisitResponse registerVisit(@Valid @RequestBody RegisterVisitRequest request) {
        return new RegisterVisitResponse(visitService.registerVisit(request));
    }

    @PutMapping("/{visitId}/approve")
    public Visit approveVisit(@PathVariable Long visitId) {
        // FIXME: 권한 체크 필요
        return visitService.approveVisit(visitId);
    }

    @PutMapping("/{visitId}/cancel")
    public Visit cancelVisit(@PathVariable Long visitId) {
        // FIXME: 권한 체크 필요
        return visitService.cancelVisit(visitId);
    }

    @PutMapping("/{visitId}/start")
    public Visit startVisit(@PathVariable Long visitId) {
        // FIXME: 권한 체크 필요
        return visitService.startVisit(visitId);
    }

    @PutMapping("/{visitId}/complete")
    public Visit completeVisit(@PathVariable Long visitId,
                               @Valid @RequestBody CompleteVisitRequest request) {
        // FIXME: 권한 체크 필요
        return visitService.completeVisit(visitId, request);
    }

    @PutMapping("/{visitId}/miss")
    public Visit missVisit(@PathVariable Long visitId) {
        // FIXME: 권한 체크 필요
        return visitService.missVisit(visitId);
    }

    @PostMapping("/{visitId}/results")
    public VisitResult createVisitResult(@PathVariable Long visitId,
                                         @Valid @RequestBody RegisterVisitResultRequest request) {
        return visitService.registerResult(visitId, request);
    }

}
