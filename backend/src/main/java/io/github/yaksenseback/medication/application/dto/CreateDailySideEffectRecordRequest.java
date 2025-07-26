package io.github.yaksenseback.medication.application.dto;

import io.github.yaksenseback.medication.domain.SideEffectLevel;
import io.github.yaksenseback.medication.domain.SideEffectType;

public record CreateDailySideEffectRecordRequest(
        Long memberId, SideEffectType sideEffectType, SideEffectLevel sideEffectLevel, String sideEffectNote
) {
}
