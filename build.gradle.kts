plugins {
    application
    java
}

group = "com.jpmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

var junitVersion = "5.8.2"
var jetbrainsAnnation = "23.0.0"
var moshiVersion = "1.13.0"
var jsonAssertVersion = "1.5.0"

dependencies {
    compileOnly("org.jetbrains:annotations:$jetbrainsAnnation")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.skyscreamer:jsonassert:$jsonAssertVersion")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.jpmc.theater.Application")
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}
