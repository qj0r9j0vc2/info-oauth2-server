pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "info-oauth2-server"

include(":application-core")
include(":application-resource")
include(":application-auth")
