package com.enchf.talks.javaday

import org.reflections.Reflections
import spock.lang.Specification
import spock.util.EmbeddedSpecRunner

class GroovyThing {

    static def TESTS_PACKAGE = "com.enchf.talks.javaday.tests"

    static void main(String[] args) {
        def specifications = new Reflections(TESTS_PACKAGE).getSubTypesOf(Specification)
        def runner = new EmbeddedSpecRunner()

        specifications.each { runner.runClass(it) }
    }
}
