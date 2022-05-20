package com.minshigee.dataserver.domain.model3d;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ObjectStorageClient {

    @Value("${spring.objectstorage.accessurl.master}")
    private String masterObjectStoragePrefixUrl;

    private final String localObjectStorageNamespaceName = "axgga8ceqe0b";
    private final String localObjectStorageBucketName = "Metajou3dModels";
    private final String localObjectStorageRegionName = "ap-chuncheon-1";


    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.create(masterObjectStoragePrefixUrl);
    }

    public Mono<String> replicationConstDataToUser(String userName, String avatarCode) {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("sourceObjectName","/const/" + avatarCode + ".fbx");
        bodyValues.add("destinationRegion",localObjectStorageRegionName);
        bodyValues.add("destinationNamespace",localObjectStorageNamespaceName);
        bodyValues.add("destinationBucket",localObjectStorageBucketName);
        bodyValues.add("destinationObjectName","/usr/" + userName + ".fbx");
        return webClient.post().uri("/actions/copyObject")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(String.class);
    }

}
