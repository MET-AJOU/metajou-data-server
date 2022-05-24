package com.minshigee.dataserver.domain.character;

import com.minshigee.dataserver.domain.character.dto.GetCharacterDto;
import com.minshigee.dataserver.domain.character.dto.UpdateCharacterDto;
import com.minshigee.dataserver.domain.profile.ProfileService;
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
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "사용자의 인증정보를 기반으로 캐릭터(아바타) 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터(아바타) 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetCharactor.class))}
            )})
    public Mono<ResponseEntity> getMyCharactor(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(characterService.getCharacter(user))
                .build().toMonoEntity();
    }

    @PostMapping
    @Operation(summary = "사용자의 인증정보를 기반으로 캐릭터(아바타) 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터(아바타) 정보를 반환합니다.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiCallGetCharactor.class))}
            )})
    public Mono<ResponseEntity> updateMyCharacter(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateCharacterDto updateDto) {
        return BaseResponse.builder()
                .body(characterService.updateCharactor(user,updateDto))
                .build().toMonoEntity();
    }

    private class ApiCallGetCharactor extends ResponseWrapper<GetCharacterDto> {}

}
