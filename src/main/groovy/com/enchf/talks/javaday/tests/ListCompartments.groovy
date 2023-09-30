package com.enchf.talks.javaday.tests

import com.enchf.talks.javaday.helpers.OCI
import com.oracle.bmc.identity.model.Compartment
import com.oracle.bmc.identity.requests.ListCompartmentsRequest
import spock.lang.Specification

class ListCompartments extends Specification implements OCI {
    def "Ensure that at least one compartment is listed" () {
        when:
        "List all compartments"
        ListCompartmentsRequest request = ListCompartmentsRequest
                .builder()
                .accessLevel(ListCompartmentsRequest.AccessLevel.Accessible)
                .compartmentId(client.getTenantId())
                .build()

        List<Compartment> compartments = client.listCompartments(request).items

        then:
        "At least one compartment is listed"
        !compartments.isEmpty()
    }
}
