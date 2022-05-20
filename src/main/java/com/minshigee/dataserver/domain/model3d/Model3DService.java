package com.minshigee.dataserver.domain.model3d;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class Model3DService {

    @Value("${spring.objectstorage.accessurl.readusr}")
    private String accessUserObjectPrefixUrl;
    @Value("${spring.objectstorage.accessurl.readconst}")
    private String accessConstObjectPrefixUrl;


}
