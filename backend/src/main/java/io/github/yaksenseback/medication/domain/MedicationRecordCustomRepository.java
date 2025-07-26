package io.github.yaksenseback.medication.domain;

import java.util.List;

public interface MedicationRecordCustomRepository {

    /**
     * 월별 복약현황 조회용
     */
    List<MedicationReportDto> getMedicationReportByMemberId(Long memberId);

}
