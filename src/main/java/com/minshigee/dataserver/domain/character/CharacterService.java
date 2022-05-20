package com.minshigee.dataserver.domain.character;

import com.minshigee.dataserver.domain.character.entity.Character;
import com.minshigee.dataserver.domain.character.dto.GetCharacterDto;
import com.minshigee.dataserver.domain.character.dto.UpdateCharacterDto;
import com.minshigee.dataserver.exception.ErrorCode;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    public Mono<GetCharacterDto> getCharacter(CustomUser user) {
        return getCharactorFromRepo(user).flatMap(charactor -> Mono.just(charactor.extractGetCharacterDto()));
    }

    public Mono<GetCharacterDto> updateCharactor(CustomUser user, UpdateCharacterDto updateDto) {
        return getCharactorFromRepo(user).doOnNext(charactor -> updateDto.updateCharactor(charactor))
                .flatMap(charactor -> {
                    if (charactor.getAvailableChangeCnt() < 0L)
                        return Mono.error(ErrorCode.CANT_UPDATE_CHARACTOR_LEAK_CNT.build());
                    return Mono.just(charactor);
                })
                .flatMap(charactor -> characterRepository.save(charactor))
                .doOnError(throwable -> ErrorCode.CANT_UPDATE_CHARACTOR.build())
                .flatMap(charactor -> Mono.just(charactor.extractGetCharacterDto()));
    }

    private Mono<Character> getCharactorFromRepo(CustomUser user) {
        return characterRepository.findCharactorByUserCode(user.getUserCode())
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_GET_CHARACTOR.build()))
                .switchIfEmpty(registerCharactor(user));
    }

    private Mono<Character> registerCharactor(CustomUser user) {
        return characterRepository.save(Character.createCharacterUsingAuthInfo(user))
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_REGISTER_CHARACTOR.build()));
    }
}
