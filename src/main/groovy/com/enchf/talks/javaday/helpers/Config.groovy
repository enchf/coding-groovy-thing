package com.enchf.talks.javaday.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.apache.commons.io.IOUtils
import spock.lang.Shared

import java.nio.charset.Charset

trait Config {
    static def CONFIG_FILE = "conventions.yaml"

    @Shared
    Map<String, Map<String, ?>> config = new ObjectMapper(new YAMLFactory())
        .readValue(IOUtils.toString(Config.classLoader.getResourceAsStream(CONFIG_FILE), Charset.defaultCharset()), Map)
}