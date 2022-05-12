package com.minshigee.dataserver.domain.charactor;

import com.minshigee.dataserver.domain.charactor.dto.GetCharactorDto;
import com.minshigee.dataserver.domain.charactor.dto.UpdateCharactorDto;
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
@RequestMapping("/api/charactor")
@RequiredArgsConstructor
public class CharactorController {
    private final CharactorService charactorService;

    @GetMapping
    @Operation(summary = "사용자의 인증정보를 기반으로 캐릭터(아바타) 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터(아바타) 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetCharactor.class))}
            )})
    public Mono<ResponseEntity> getMyCharactor(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(charactorService.getCharactor(user))
                .build().toMonoEntity();
    }

    @PostMapping
    @Operation(summary = "사용자의 인증정보를 기반으로 캐릭터(아바타) 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터(아바타) 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetCharactor.class))}
            )})
    public Mono<ResponseEntity> updateMyCharactor(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateCharactorDto updateDto) {
        return BaseResponse.builder()
                .body(charactorService.updateCharactor(user,updateDto))
                .build().toMonoEntity();
    }

    private class ApiCallGetCharactor extends ResponseWrapper<GetCharactorDto> {}

}
