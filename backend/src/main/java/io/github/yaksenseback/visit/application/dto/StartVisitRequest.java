package io.github.yaksenseback.visit.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StartVisitRequest {

    private final LocalDateTime startDate;

    public StartVisitRequest(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
