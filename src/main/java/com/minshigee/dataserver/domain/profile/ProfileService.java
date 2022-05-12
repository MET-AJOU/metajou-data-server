package com.minshigee.dataserver.domain.profile;

import com.minshigee.dataserver.domain.profile.entity.Profile;
import com.minshigee.dataserver.domain.profile.dto.GetProfileDto;
import com.minshigee.dataserver.domain.profile.dto.UpdateProfileDto;
import com.minshigee.dataserver.exception.ErrorCode;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Mono<GetProfileDto> getProfile(CustomUser user) {
        return getProfileFromRepo(user).flatMap(profile -> Mono.just(profile.extractGetProfile()));
    }

    public Mono<GetProfileDto> updateProfile(CustomUser user, UpdateProfileDto updateDto) {
        return getProfileFromRepo(user).doOnNext(profile -> updateDto.updateProfile(profile))
                .flatMap(profile -> profileRepository.save(profile))
                .flatMap(profile -> Mono.just(profile.extractGetProfile()))
                .doOnError(throwable -> ErrorCode.CANT_UPDATE_PROFILE.build());
    }

    private Mono<Profile> getProfileFromRepo(CustomUser user) {
        return profileRepository.findProfileByUserCode(user.getUserCode())
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_GET_PROFILE.build()))
                .switchIfEmpty(registerProfile(user));
    }

    private Mono<Profile> registerProfile(CustomUser user) {
        return profileRepository.save(Profile.createProfileByAuthUserInfo(user))
                .doOnError(throwable -> Mono.error(ErrorCode.CANT_REGISTER_PROFILE.build()));
    }
}
