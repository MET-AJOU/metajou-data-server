package com.minshigee.dataserver.domain.character;

import com.minshigee.dataserver.domain.character.entity.Character;
import com.minshigee.dataserver.domain.character.dto.GetCharacterDto;
import com.minshigee.dataserver.domain.character.dto.UpdateCharacterDto;
import com.minshigee.dataserver.domain.profile.ProfileService;
import com.minshigee.dataserver.domain.utils.ModelObjectStorageUtil;
import com.minshigee.dataserver.exception.ErrorCode;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ProfileService profileService;
    private final ModelObjectStorageUtil modelObjectStorageUtil;

    public Mono<GetCharacterDto> getCharacter(CustomUser user) {
        return getCharactorFromRepo(user).flatMap(charactor -> Mono.just(charactor.extractGetCharacterDto()));
    }

    public Mono<GetCharacterDto> updateCharactor(CustomUser user, UpdateCharacterDto updateDto) {
        return Mono.zip(getCharactorFromRepo(user), profileService.getProfile(user))
                .flatMap(tuple -> {
                    Character character = updateDto.updateCharactor(tuple.getT1());
                    if (character.getAvailableChangeCnt() < 0L)
                        return Mono.error(ErrorCode.CANT_UPDATE_CHARACTOR_LEAK_CNT.build());
                    return modelObjectStorageUtil.copyObject(
                            "const/"+character.getAvatarCustomCode() + ".fbx"
                                    , "usr/" + tuple.getT2().getUserName() + ".fbx")
                            .then(characterRepository.save(character));
                })
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
