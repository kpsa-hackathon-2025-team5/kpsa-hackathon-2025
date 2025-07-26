package io.github.yaksenseback.visit.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompleteVisitRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime endDate;
    private final String resultInstructions;

    public CompleteVisitRequest(LocalDateTime endDate, String resultInstructions) {
        this.endDate = endDate;
        this.resultInstructions = resultInstructions;
    }
}
