dependencies {
    implementation(project(":application-core"))
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.5.4")
    runtimeOnly("mysql:mysql-connector-java:8.0.32")
}
