package com.minshigee.dataserver.domain.profile;

import com.minshigee.dataserver.domain.profile.entity.dto.UpdateProfileDto;
import com.minshigee.dataserver.response.BaseResponse;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public Mono<ResponseEntity> getMyProfile(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(profileService.getProfile(user))
                .build().toMonoEntity();
    }

    @PostMapping
    public Mono<ResponseEntity> updateMyProfile(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateProfileDto updateDto) {
        return BaseResponse.builder()
                .body(profileService.updateProfile(user, updateDto))
                .build().toMonoEntity();
    }

}
