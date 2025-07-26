package io.github.yaksenseback.medication.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicationRecordRepositoryImpl implements MedicationRecordCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JPAQueryFactory factory;


    @Override
    public List<MedicationReportDto> getMedicationReportByMemberId(Long memberId) {
        QMedicationRecord medicationRecord = QMedicationRecord.medicationRecord;
        QMedicationPrescription medicationPrescription = QMedicationPrescription.medicationPrescription;
        QMedicationSchedule medicationSchedule = QMedicationSchedule.medicationSchedule;


        return List.of();
    }

}
