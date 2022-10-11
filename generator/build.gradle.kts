plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"

    idea
    application

    alias(detektCatalog.plugins.detekt)
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    detektPlugins(detektCatalog.formatting)
}

application {
    mainClass.set("dev.labuda.spreadsheets.AppKt")
}

detekt {
    config = rootProject.files("detekt.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_17.majorVersion
}
