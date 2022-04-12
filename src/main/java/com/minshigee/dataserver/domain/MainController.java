package com.minshigee.dataserver.domain;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class MainController {

    @Operation(summary = "API 홈페이지로 리다이렉트합니다.")
    @GetMapping("/api")
    public Mono<ResponseEntity> getSwaggerHome() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/webjars/swagger-ui/index.html"))
                .build());
    }
}
