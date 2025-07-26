package io.github.yaksenseback.member.domain;

import java.util.Optional;

public interface MemberCustomRepository {

    public <T extends Member> Optional<T> findById(Long id, Class<T> type);

    <T extends Member> Optional<T> findByUsername(String username, Class<T> type);

}
