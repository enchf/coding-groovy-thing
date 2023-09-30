package com.enchf.talks.javaday.helpers

import com.oracle.bmc.auth.AuthenticationDetailsProvider
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import com.oracle.bmc.identity.Identity
import com.oracle.bmc.identity.IdentityClient
import com.oracle.bmc.identity.model.Compartment
import com.oracle.bmc.identity.requests.CreateCompartmentRequest
import com.oracle.bmc.identity.requests.DeleteCompartmentRequest
import com.oracle.bmc.identity.requests.ListCompartmentsRequest
import com.oracle.bmc.identity.responses.CreateCompartmentResponse
import com.oracle.bmc.objectstorage.ObjectStorage
import com.oracle.bmc.objectstorage.ObjectStorageClient
import com.oracle.bmc.objectstorage.model.Bucket
import com.oracle.bmc.objectstorage.requests.CreateBucketRequest
import com.oracle.bmc.objectstorage.requests.DeleteBucketRequest
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest
import com.oracle.bmc.objectstorage.responses.CreateBucketResponse
import groovy.util.logging.Slf4j

@Slf4j
@Singleton
class Client implements Release {
    static def DEFAULT_PROFILE = "DEFAULT"

    @Delegate(includes = "getTenantId", interfaces = false)
    AuthenticationDetailsProvider authProvider = new ConfigFileAuthenticationDetailsProvider(DEFAULT_PROFILE)

    @Lazy
    @Delegate(excludes = ["getWaiters", "getPaginators"], interfaces = false)
    Identity identity = {
        IdentityClient
                .builder()
                .build(authProvider)
    }()

    @Lazy
    @Delegate(excludes = ["getWaiters", "getPaginators"], interfaces = false)
    ObjectStorage objectStorage = {
        ObjectStorageClient
            .builder()
            .build(authProvider)
    }()

    CreateCompartmentResponse createCompartment(CreateCompartmentRequest request) {
        identity.createCompartment(request)
                .tap { log.info("Compartment created with OCID ${it.compartment.id}") }
                .tap { registerCompartmentInRelease() }
                .tap { appendForRelease(it.compartment) }
    }

    CreateBucketResponse createBucket(CreateBucketRequest request) {
        objectStorage.createBucket(request)
                     .tap { log.info("Compartment created with OCID ${it.bucket.id}") }
                     .tap { registerBucketInRelease() }
                     .tap { appendForRelease(it.bucket) }
    }

    Compartment findCompartment(String name) {
        identity.listCompartments(ListCompartmentsRequest.builder().compartmentId(getTenantId()).build())
                .items
                .find { it.name == name }
    }

    String getNameSpace(String compartmentId) {
        getNamespace(GetNamespaceRequest.builder().compartmentId(compartmentId).build()).value
    }

    private def registerCompartmentInRelease() {
        registerClass(Compartment, { Compartment compartment ->
            def deleteRequest = DeleteCompartmentRequest.builder().compartmentId(compartment.id).build()
            identity.deleteCompartment(deleteRequest)
        })
    }

    private def registerBucketInRelease() {
        registerClass(Bucket, { Bucket bucket ->
            def deleteBucket = DeleteBucketRequest
                    .builder()
                    .bucketName(bucket.name)
                    .namespaceName(getNameSpace(bucket.compartmentId))
                    .build()
            objectStorage.deleteBucket(deleteBucket)
        })
    }
}
