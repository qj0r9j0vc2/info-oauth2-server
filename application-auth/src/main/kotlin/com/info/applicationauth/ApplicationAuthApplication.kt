package com.info.applicationauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ApplicationAuthApplication

fun main(args: Array<String>) {
    runApplication<ApplicationAuthApplication>(*args)
}
