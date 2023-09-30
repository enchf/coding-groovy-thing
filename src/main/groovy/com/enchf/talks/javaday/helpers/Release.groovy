package com.enchf.talks.javaday.helpers

import groovy.util.logging.Slf4j

@Slf4j
trait Release {
    Map<Class<?>, Closure> handlers = [:]
    Map<Class<?>, List<Object>> createdObjects = [:]

    def <T> void registerClass(Class<T> clazz, Closure deleter) {
        handlers.computeIfAbsent(clazz, { deleter })
        createdObjects.computeIfAbsent(clazz, { [] })
    }

    def <T> void appendForRelease(T object) {
        createdObjects[object.class] << object
    }

    def releaseAll() {
        createdObjects.each {
            it.value
              .each { log.info("Put object of type ${it.class} with id ${it.id} to delete") }
              .each { object -> handlers[it.key](object) }
            it.value.clear()
        }
    }
}
