package com.minshigee.dataserver.domain.charactor;

import com.minshigee.dataserver.domain.charactor.entity.Charactor;
import com.minshigee.dataserver.domain.charactor.entity.dto.GetCharactorDto;
import com.minshigee.dataserver.domain.charactor.entity.dto.UpdateCharactorDto;
import com.minshigee.dataserver.exception.ErrorCode;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CharactorService {

    private final CharactorRepository charactorRepository;

    public Mono<GetCharactorDto> getCharactor(CustomUser user) {
        return getCharactorFromRepo(user).flatMap(charactor -> Mono.just(charactor.extractGetCharactorDto()));
    }

    public Mono<GetCharactorDto> updateCharactor(CustomUser user, UpdateCharactorDto updateDto) {
        return getCharactorFromRepo(user).doOnNext(charactor -> updateDto.updateCharactor(charactor))
                .flatMap(charactor -> {
                    if (charactor.getAvailableChangeCnt() < 0L)
                        return Mono.error(ErrorCode.CANT_UPDATE_CHARACTOR_LEAK_CNT.build());
                    return Mono.just(charactor);
                })
                .flatMap(charactor -> charactorRepository.save(charactor))
                .doOnError(throwable -> ErrorCode.CANT_UPDATE_CHARACTOR.build())
                .flatMap(charactor -> Mono.just(charactor.extractGetCharactorDto()));
    }

    private Mono<Charactor> getCharactorFromRepo(CustomUser user) {
        return charactorRepository.findCharactorByUserCode(user.getUserCode())
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_GET_CHARACTOR.build()))
                .switchIfEmpty(registerCharactor(user));
    }

    private Mono<Charactor> registerCharactor(CustomUser user) {
        return charactorRepository.save(Charactor.createCharactorUsingAuthInfo(user))
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_REGISTER_CHARACTOR.build()));
    }
}
