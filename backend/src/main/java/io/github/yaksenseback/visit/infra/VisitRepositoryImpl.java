package io.github.yaksenseback.visit.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.yaksenseback.visit.domain.QVisit;
import io.github.yaksenseback.visit.domain.Visit;
import io.github.yaksenseback.visit.domain.VisitCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VisitRepositoryImpl implements VisitCustomRepository {

    private final JPAQueryFactory queryFactory;


    public List<Visit> findAllVisitsByPatient(Long patientId) {
        QVisit qVisit = QVisit.visit;

        return queryFactory.selectFrom(QVisit.visit)
                .where(QVisit.visit.patient.id.eq(patientId))
                .orderBy(qVisit.scheduledStartDateTime.desc())
                .fetch();
    }

    public List<Visit> findAllVisitsByPharmacist(Long pharmacistId) {
        QVisit qVisit = QVisit.visit;

        return queryFactory.selectFrom(QVisit.visit)
                .where(QVisit.visit.pharmacist.id.eq(pharmacistId))
                .orderBy(qVisit.scheduledStartDateTime.desc())
                .fetch();
    }

}
