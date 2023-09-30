package com.enchf.talks.javaday.helpers

import com.oracle.bmc.Region
import com.oracle.bmc.auth.AuthenticationDetailsProvider
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import com.oracle.bmc.identity.Identity
import com.oracle.bmc.identity.IdentityClient
import com.oracle.bmc.identity.requests.CreateCompartmentRequest
import com.oracle.bmc.identity.requests.DeleteCompartmentRequest
import com.oracle.bmc.identity.responses.CreateCompartmentResponse
import groovy.util.logging.Slf4j

@Slf4j
@Singleton
class Client {
    static def DEFAULT_PROFILE = "DEFAULT"

    @Delegate(includes = "getTenantId", interfaces = false)
    AuthenticationDetailsProvider authProvider = new ConfigFileAuthenticationDetailsProvider(DEFAULT_PROFILE)

    List<String> compartments = []

    @Lazy
    @Delegate
    Identity identity = {
        IdentityClient
                .builder()
                .build(authProvider)
    }()

    CreateCompartmentResponse createCompartment(CreateCompartmentRequest request) {
        identity.createCompartment(request)
                .tap { log.info("Compartment created with OCID ${it.compartment.id}") }
                .tap { compartments << it.compartment.id }
    }

    def releaseResources() {
        compartments.each {
            def deleteRequest = DeleteCompartmentRequest.builder().compartmentId(it).build()
            identity.deleteCompartment(deleteRequest)
        }
        compartments.clear()
    }
}
