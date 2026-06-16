plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    /**
     * Server
     */
    implementation(platform("io.netty:netty-bom:4.2.14.Final"))

    implementation("io.netty:netty-transport")
    implementation("io.netty:netty-codec-http")
    implementation("io.netty:netty-handler")
    implementation("io.netty:netty-buffer")
    implementation("io.netty:netty-common")
    implementation("io.netty:netty-resolver")

    /**
     * Testing
     */
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    /**
     * Utils & Logging
     */
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")

    /**
     * Database
     */
    implementation("org.postgresql:postgresql:42.7.3")
}

tasks.test {
    useJUnitPlatform()
}