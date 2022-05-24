package com.minshigee.dataserver.domain.character;

import com.minshigee.dataserver.domain.character.entity.Character;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CharacterRepository extends ReactiveCrudRepository<Character, Long> {
    public Mono<Character> findCharactorByUserCode(Long userCode);
}
