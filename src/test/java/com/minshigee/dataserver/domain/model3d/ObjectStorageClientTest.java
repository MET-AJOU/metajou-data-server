package com.minshigee.dataserver.domain.model3d;

import com.minshigee.dataserver.domain.utils.ModelObjectStorageUtil;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CopyObjectDetails;
import com.oracle.bmc.objectstorage.requests.CopyObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.responses.CopyObjectResponse;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {ModelObjectStorageUtil.class})
public class ObjectStorageClientTest {

    @Autowired
    ModelObjectStorageUtil util;

    @Test
    public void testCopyModelObjectStorage() throws Exception {
        util.copyObject("const/0.0.0.0.fbx","usr/alstjr1642.fbx").subscribe();
        while(true);
    }

    @Test
    public void testDeleteModelObjectStorage() throws Exception {
        Flux.interval(Duration.ofSeconds(1l)).filter(i -> i <= 22 && i >= 1)
                .flatMap(i -> util.deleteObject("usr/" + i + ".fbx")).subscribe();
        while(true);
    }

}
