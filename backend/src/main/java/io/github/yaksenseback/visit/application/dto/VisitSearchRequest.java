package io.github.yaksenseback.visit.application.dto;

import io.github.yaksenseback.visit.domain.VisitStatus;

import java.time.LocalDate;

public record VisitSearchRequest(
        VisitStatus status,
        LocalDate startDate,
        LocalDate endDate
) {
}
