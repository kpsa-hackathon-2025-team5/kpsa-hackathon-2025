package io.github.yaksenseback.medication.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class MedicationScheduleRepositoryImpl implements MedicationScheduleCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JPAQueryFactory factory;


}
