package com.minshigee.dataserver.domain.charactor;

import com.minshigee.dataserver.domain.charactor.entity.Charactor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CharactorRepository extends ReactiveCrudRepository<Charactor, Long> {
    public Mono<Charactor> findCharactorByUserCode(Long userCode);
}
