package io.github.yaksenseback.medication.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MedicationScheduleRepositoryImpl implements MedicationScheduleCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JPAQueryFactory factory;


    @Override
    public List<MedicationSchedule> findAllMedicationSchedules(Long memberId, TimeLabel timeLabel, LocalDate date) {
        QMedicationRecord medicationRecord = QMedicationRecord.medicationRecord;
        QMedicationSchedule medicationSchedule = QMedicationSchedule.medicationSchedule;

        return factory.select(medicationSchedule)
                .from(medicationRecord)
                .join(medicationSchedule).on(medicationRecord.medicationSchedule.id.eq(medicationSchedule.id))
                .where(medicationRecord.memberId.eq(memberId)
                        .and(medicationSchedule.timeLabel.eq(timeLabel))
                        .and(medicationRecord.createdAt.between(
                                date.atStartOfDay(),
                                date.atTime(23, 59, 59)
                        )))
                .fetch();
    }

}
