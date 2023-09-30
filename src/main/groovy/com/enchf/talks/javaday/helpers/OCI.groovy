package com.enchf.talks.javaday.helpers

import spock.lang.Shared

trait OCI {
    @Shared
    Client client = Client.instance

    def cleanup() {
        client.releaseResources()
    }
}
