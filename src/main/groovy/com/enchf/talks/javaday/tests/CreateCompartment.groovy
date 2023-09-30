package com.enchf.talks.javaday.tests

import com.enchf.talks.javaday.helpers.OCI
import com.oracle.bmc.identity.model.Compartment
import com.oracle.bmc.identity.model.CreateCompartmentDetails
import com.oracle.bmc.identity.requests.CreateCompartmentRequest
import com.oracle.bmc.model.BmcException
import spock.lang.Specification

class CreateCompartment extends Specification implements OCI {
    def "Create a compartment" () {
        given:
        "Compartment details"
        String validName = "a-valid-name"
        CreateCompartmentDetails details = buildDetails(validName)

        when:
        "A compartment is created"
        Compartment compartment = createCompartment(details)

        then:
        "Compartment must be created with the details provided"
        compartment != null
        compartment.name == validName
    }

    def "Invalid parameters creating a compartment" (String invalidName) {
        when:
        "A compartment is tried to be created with invalid arguments"
        Compartment compartment = createCompartment(buildDetails(invalidName))

        then:
        def exception = thrown(BmcException)
        exception.statusCode >= 400
        exception.statusCode < 500

        where:
        invalidName | _
        null        | _
        "%^&(#@&#@" | _
    }

    private CreateCompartmentDetails buildDetails(String name) {
        CreateCompartmentDetails
                .builder()
                .compartmentId(client.getTenantId())
                .name(name)
                .description("Test Compartment")
                .build()
    }

    private Compartment createCompartment(CreateCompartmentDetails details) {
        client
            .createCompartment(CreateCompartmentRequest.builder().createCompartmentDetails(details).build())
            .compartment
    }
}
