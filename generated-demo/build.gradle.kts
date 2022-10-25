@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    idea
    application

    alias(kotlinCatalog.plugins.jvm)
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

detekt {
    config = rootProject.files("detekt.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_17.majorVersion

    // Ignore automatically generated source
    exclude("**/GeneratedThreatTypeResolver.kt")
}
