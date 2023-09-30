package com.enchf.talks.javaday.helpers

import com.oracle.bmc.auth.AuthenticationDetailsProvider
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import spock.lang.Shared

trait OCI {
    static def DEFAULT_PROFILE = "DEFAULT"

    @Shared
    AuthenticationDetailsProvider authProvider = new ConfigFileAuthenticationDetailsProvider(DEFAULT_PROFILE)
}
