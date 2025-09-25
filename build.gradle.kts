import com.avast.gradle.dockercompose.ComposeExtension

plugins {
    kotlin("jvm") version "2.1.21"
    id("org.jetbrains.compose") version "1.8.2"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21"
    id("com.avast.gradle.docker-compose") version "0.17.7"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

extensions.configure<ComposeExtension> {
    useComposeFiles.set(listOf("compose.yml"))
    stopContainers.set(true)
    removeContainers.set(true)
    removeVolumes.set(true)
    waitForTcpPorts.set(true)
    captureContainersOutput.set(true)

    environment.put("DB_PORT", "5432")
    environment.put("DB_HOST", "localhost")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
    implementation("org.postgresql:postgresql:42.7.4")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.register("runApp"){
    group = "Application"
    dependsOn("build",
        "composeUp", "run")
    finalizedBy("composeDown")
}