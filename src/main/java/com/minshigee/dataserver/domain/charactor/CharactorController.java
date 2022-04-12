package com.minshigee.dataserver.domain.charactor;

import com.minshigee.dataserver.domain.charactor.entity.dto.UpdateCharactorDto;
import com.minshigee.dataserver.response.BaseResponse;
import com.minshigee.dataserver.security.entity.CustomUser;
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
    public Mono<ResponseEntity> getMyCharactor(@AuthenticationPrincipal CustomUser user) {
        return BaseResponse.builder()
                .body(charactorService.getCharactor(user))
                .build().toMonoEntity();
    }

    @PostMapping
    public Mono<ResponseEntity> updateMyCharactor(@AuthenticationPrincipal CustomUser user, @RequestBody UpdateCharactorDto updateDto) {
        return BaseResponse.builder()
                .body(charactorService.updateCharactor(user,updateDto))
                .build().toMonoEntity();
    }


}
