package io.github.yaksenseback.member.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.yaksenseback.member.domain.Member;
import io.github.yaksenseback.member.domain.MemberCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepositoryImpl implements MemberCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JPAQueryFactory factory;


    @Override
    public <T extends Member> Optional<T> findById(Long id, Class<T> type) {
        try {
            T result = entityManager.find(type, id);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public <T extends Member> Optional<T> findByUsername(String username, Class<T> type) {
        return Optional.empty();
    }


}
