package com.enchf.talks.javaday.tests

import com.enchf.talks.javaday.helpers.OCI
import com.oracle.bmc.Region
import com.oracle.bmc.identity.Identity
import com.oracle.bmc.identity.IdentityClient
import com.oracle.bmc.identity.model.Compartment
import com.oracle.bmc.identity.requests.ListCompartmentsRequest
import spock.lang.Specification

class ListCompartments extends Specification implements OCI {
    def "Ensure that at least one compartment is listed" () {
        when:
        "Create an Identity client and list all compartments"
        Identity client = IdentityClient
                .builder()
                .region(Region.MX_QUERETARO_1)
                .build(authProvider)

        ListCompartmentsRequest request = ListCompartmentsRequest
                .builder()
                .accessLevel(ListCompartmentsRequest.AccessLevel.Accessible)
                .compartmentId(authProvider.tenantId)
                .build()

        List<Compartment> compartments = client.listCompartments(request).items

        then:
        "At least one compartment is listed"
        !compartments.isEmpty()
    }
}
