package com.enchf.talks.javaday.helpers

import spock.lang.Shared

trait OCI extends Config {
    @Shared
    Client client = Client.instance

    def cleanup() {
        client.releaseAll()
    }
}
