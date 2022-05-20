package com.minshigee.dataserver.domain.model3d;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ObjectStorageClientTest {

    private final String masterObjectStoragePrefixUrl = "https://objectstorage.ap-chuncheon-1.oraclecloud.com/p/qL3GahaUtYDzmzzgjsWlNtLjBJ4C60FKnsdWKvCt7qw1tYMWQ2JtFfNbu_x3sQEI/n/axgga8ceqe0b/b/Metajou3dModels";
    private final String localObjectStorageNamespaceName = "axgga8ceqe0b";
    private final String localObjectStorageBucketName = "Metajou3dModels";
    private final String localObjectStorageRegionName = "ap-chuncheon-1";
    private WebClient webClient = WebClient.create(masterObjectStoragePrefixUrl);

    @Test
    public void updateDataTest() {
        replicationConstDataToUser("minshigee", "0.0.0.0").subscribe(s -> {System.err.println(s);});
        while(true);
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
