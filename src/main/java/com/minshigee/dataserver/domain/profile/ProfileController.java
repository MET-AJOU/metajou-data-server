package com.minshigee.dataserver.domain.profile;

import com.minshigee.dataserver.domain.profile.entity.dto.GetProfileDto;
import com.minshigee.dataserver.domain.profile.entity.dto.UpdateProfileDto;
import com.minshigee.dataserver.response.BaseResponse;
import com.minshigee.dataserver.response.ResponseWrapper;
import com.minshigee.dataserver.security.entity.CustomUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "사용자의 인증정보를 기반으로 프로필 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetProfile.class))}
            )})
    public Mono<ResponseEntity> getMyProfile(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(profileService.getProfile(user))
                .build().toMonoEntity();
    }

    @PostMapping
    @Operation(summary = "사용자의 인증정보 기반의 프로필 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정된 프로필 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetProfile.class))}
            )})
    public Mono<ResponseEntity> updateMyProfile(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateProfileDto updateDto) {
        return BaseResponse.builder()
                .body(profileService.updateProfile(user, updateDto))
                .build().toMonoEntity();
    }

    private class ApiCallGetProfile extends ResponseWrapper<GetProfileDto> {}

}
