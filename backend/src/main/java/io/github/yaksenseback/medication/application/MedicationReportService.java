package io.github.yaksenseback.medication.application;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.yaksenseback.medication.application.dto.DailyComplianceResponse;
import io.github.yaksenseback.medication.application.dto.TimeSlotComplianceResponse;
import io.github.yaksenseback.medication.domain.MedicationRecordRepository;
import io.github.yaksenseback.medication.domain.QMedicationPrescription;
import io.github.yaksenseback.medication.domain.QMedicationRecord;
import io.github.yaksenseback.medication.domain.QMedicationSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationReportService {

    private final MedicationRecordRepository medicationRecordRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * 월별 달력용 일자별 복약 현황 조회
     */
    public List<DailyComplianceResponse> getDailyCompliance(
            Long memberId,
            LocalDate startDate,
            LocalDate endDate) {

        List<DailyComplianceResponse> result = new ArrayList<>();

        // 각 날짜별로 처리
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int requiredCount = getRequiredMedicationCount(memberId, date);
            int takenCount = getTakenMedicationCount(memberId, date);

            int complianceResult;
            if (requiredCount == 0) {
                complianceResult = 0; // 복용할 약이 없음
            } else if (takenCount == 0) {
                complianceResult = 2; // 모두 복용하지 않음
            } else if (takenCount == requiredCount) {
                complianceResult = 0; // 완전 복용
            } else {
                complianceResult = 1; // 부분 복용
            }

            result.add(new DailyComplianceResponse(date.toString(), complianceResult));
        }

        return result;
    }

    /**
     * 특정 날짜에 복용해야 하는 약물 개수 조회
     */
    private int getRequiredMedicationCount(Long memberId, LocalDate targetDate) {
        QMedicationSchedule ms = QMedicationSchedule.medicationSchedule;
        QMedicationPrescription mp = QMedicationPrescription.medicationPrescription;

        Long count = queryFactory
                .select(ms.count())
                .from(ms)
                .join(mp).on(ms.medicationPrescription.id.eq(mp.id))
                .where(
                        ms.memberId.eq(memberId),
                        ms.isActive.isTrue(),
                        mp.isActive.isTrue(),
                        mp.startDate.loe(targetDate),
                        mp.endDate.isNull().or(mp.endDate.goe(targetDate))
                )
                .fetchOne();

        return count != null ? count.intValue() : 0;
    }

    /**
     * 특정 날짜에 실제 복용한 약물 개수 조회
     */
    private int getTakenMedicationCount(Long memberId, LocalDate targetDate) {
        QMedicationRecord mr = QMedicationRecord.medicationRecord;

        Long count = queryFactory
                .select(mr.count())
                .from(mr)
                .where(
                        mr.memberId.eq(memberId),
                        mr.isTaken.isTrue(),
                        mr.scheduledAt.goe(LocalDate.from(targetDate.atStartOfDay()))
                                .and(mr.scheduledAt.loe(LocalDate.from(targetDate.atTime(23, 59, 59))))
                )
                .fetchOne();

        return count != null ? count.intValue() : 0;
    }

    /**
     * 시간대별 복약 현황 조회 (아침, 점심, 저녁)
     */
    public List<TimeSlotComplianceResponse> getTimeSlotCompliance(
            Long memberId,
            LocalDate targetDate) {

        List<TimeSlotComplianceResponse> result = new ArrayList<>();

        // 시간대 정의
        Map<String, LocalTime[]> timeSlots = Map.of(
                "MORNING", new LocalTime[]{LocalTime.of(6, 0), LocalTime.of(11, 59)},
                "AFTERNOON", new LocalTime[]{LocalTime.of(12, 0), LocalTime.of(17, 59)},
                "EVENING", new LocalTime[]{LocalTime.of(18, 0), LocalTime.of(23, 59)}
        );

        for (Map.Entry<String, LocalTime[]> entry : timeSlots.entrySet()) {
            String timeSlot = entry.getKey();
            LocalTime startTime = entry.getValue()[0];
            LocalTime endTime = entry.getValue()[1];

            int requiredCount = getRequiredMedicationCountByTimeSlot(memberId, targetDate, startTime, endTime);
            int takenCount = getTakenMedicationCountByTimeSlot(memberId, targetDate, startTime, endTime);

            int complianceResult;
            if (requiredCount == 0) {
                complianceResult = 0;
            } else if (takenCount == 0) {
                complianceResult = 2;
            } else if (takenCount == requiredCount) {
                complianceResult = 0;
            } else {
                complianceResult = 1;
            }

            result.add(new TimeSlotComplianceResponse(timeSlot, requiredCount, takenCount, complianceResult));
        }

        return result;
    }

    private int getRequiredMedicationCountByTimeSlot(Long memberId, LocalDate targetDate, LocalTime startTime, LocalTime endTime) {
        QMedicationSchedule ms = QMedicationSchedule.medicationSchedule;
        QMedicationPrescription mp = QMedicationPrescription.medicationPrescription;

        Long count = queryFactory
                .select(ms.count())
                .from(ms)
                .join(mp).on(ms.medicationPrescription.id.eq(mp.id))
                .where(
                        ms.memberId.eq(memberId),
                        ms.isActive.isTrue(),
                        mp.isActive.isTrue(),
                        mp.startDate.loe(targetDate),
                        mp.endDate.isNull().or(mp.endDate.goe(targetDate)),
                        ms.medicationTime.between(startTime, endTime)
                )
                .fetchOne();

        return count != null ? count.intValue() : 0;
    }

    private int getTakenMedicationCountByTimeSlot(Long memberId, LocalDate targetDate, LocalTime startTime, LocalTime endTime) {
        QMedicationRecord mr = QMedicationRecord.medicationRecord;
        QMedicationSchedule ms = QMedicationSchedule.medicationSchedule;

        Long count = queryFactory
                .select(mr.count())
                .from(mr)
                .join(ms).on(mr.medicationScheduleId.eq(ms.id))
                .where(
                        mr.memberId.eq(memberId),
                        mr.isTaken.isTrue(),
                        mr.scheduledAt.goe(LocalDate.from(targetDate.atStartOfDay()))
                                .and(mr.scheduledAt.loe(LocalDate.from(targetDate.atTime(23, 59, 59)))),
                        ms.medicationTime.between(startTime, endTime)
                )
                .fetchOne();

        return count != null ? count.intValue() : 0;
    }

}
