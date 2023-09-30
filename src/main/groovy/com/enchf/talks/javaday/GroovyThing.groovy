package com.enchf.talks.javaday

import groovy.util.logging.Slf4j
import org.reflections.Reflections
import spock.lang.Specification
import spock.util.EmbeddedSpecRunner

@Slf4j
class GroovyThing {

    static def TESTS_PACKAGE = "com.enchf.talks.javaday.tests"

    static void main(String[] args) {
        def specifications = new Reflections(TESTS_PACKAGE).getSubTypesOf(Specification)
        def runner = new EmbeddedSpecRunner()

        specifications.eachWithIndex { it, index ->
            log.info("Running test case #${index + 1} of class ${it.name}")
            runner.runClass(it)
        }
    }
}
