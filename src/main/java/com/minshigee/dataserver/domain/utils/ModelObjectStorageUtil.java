package com.minshigee.dataserver.domain.utils;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CopyObjectDetails;
import com.oracle.bmc.objectstorage.requests.CopyObjectRequest;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.responses.CopyObjectResponse;
import com.oracle.bmc.objectstorage.responses.DeleteObjectResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ModelObjectStorageUtil {

    private ObjectStorageClient client;
    @Getter
    final String bucketName = "Metajou3dModels";
    @Getter
    final String namespaceName = "test";

    @PostConstruct
    private void init() throws IOException {
        ClassPathResource configResource = new ClassPathResource("oci/config");
        System.out.println(configResource.getURL());
        ConfigFileReader.ConfigFile config = ConfigFileReader.parse(configResource.getInputStream(), "DEFAULT");
        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);
        client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_CHUNCHEON_1);
    }

    public Mono<CopyObjectResponse> copyObject(String src, String des) {
        return Mono.fromCallable(() -> client.copyObject(
                CopyObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucketName)
                        .copyObjectDetails(CopyObjectDetails.builder()
                                .sourceObjectName(src)
                                .destinationObjectName(des)
                                .destinationRegion(Region.AP_CHUNCHEON_1.getRegionId())
                                .destinationBucket(bucketName)
                                .destinationNamespace(namespaceName)
                                .build())
                        .build()));
    }

    public Mono<DeleteObjectResponse> deleteObject(String src) {
        return Mono.fromCallable(() -> client.deleteObject(
                DeleteObjectRequest.builder()
                        .namespaceName(namespaceName)
                        .bucketName(bucketName)
                        .objectName(src)
                        .build()));
    }


}
