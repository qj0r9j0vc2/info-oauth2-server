import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    base
    java
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }


    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("org.springframework.data.redis.core.RedisHash")
        annotation("org.springframework.data.mongodb.core.mapping.Document")
        annotation("jakarta.persistence.Embeddable")
    }

    noArg {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("org.springframework.data.redis.core.RedisHash")
        annotation("org.springframework.data.mongodb.core.mapping.Document")
        annotation("jakarta.persistence.Embeddable")
    }

    val javaVersion = "17"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        runtimeOnly("mysql:mysql-connector-java:8.0.32")

    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
}
val bootJar: BootJar by tasks
val jar: Jar by tasks
bootJar.enabled = false
jar.enabled = true

project(":application-core") {
    val bootJar: BootJar by tasks
    val jar: Jar by tasks
    bootJar.enabled = false
    jar.enabled = true
}

project(":application-resource") {
    val bootJar: BootJar by tasks
    val jar: Jar by tasks
    bootJar.enabled = true
    jar.enabled = false

    dependencies {
        implementation(project(":application-core"))
    }
}

project(":application-auth") {
    val bootJar: BootJar by tasks
    val jar: Jar by tasks
    bootJar.enabled = true
    jar.enabled = false
    dependencies {
        implementation(project(":application-core"))
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")
    }
}
