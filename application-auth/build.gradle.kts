dependencies {
    implementation(project(":application-core"))
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.security:spring-security-oauth2-authorization-server:1.0.2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    runtimeOnly("mysql:mysql-connector-java:8.0.32")

}
