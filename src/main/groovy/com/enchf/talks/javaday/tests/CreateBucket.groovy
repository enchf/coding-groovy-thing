package com.enchf.talks.javaday.tests

import com.enchf.talks.javaday.helpers.OCI
import com.oracle.bmc.identity.model.Compartment
import com.oracle.bmc.objectstorage.model.Bucket
import com.oracle.bmc.objectstorage.model.CreateBucketDetails
import com.oracle.bmc.objectstorage.requests.CreateBucketRequest
import spock.lang.Specification

class CreateBucket extends Specification implements OCI {
    def "Create a bucket in the test compartment" () {
        given:
        "The details to create a bucket"
        String bucketName = "a-test-bucket"
        Compartment testCompartment = client.findCompartment(config.compartments.default as String)
        CreateBucketDetails details = CreateBucketDetails.builder()
            .compartmentId(testCompartment.id)
            .name(bucketName)
            .build()

        when:
        "Create bucket is requested"
        Bucket bucket = client.createBucket(CreateBucketRequest
                .builder()
                .createBucketDetails(details)
                .namespaceName(client.getNameSpace(testCompartment.id))
                .build()).bucket

        then:
        "Bucket should be created with the specified details"
        bucket != null
        bucket.name == bucketName
    }
}
