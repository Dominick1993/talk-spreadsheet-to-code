@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    idea
    application

    alias(kotlinCatalog.plugins.jvm)
    alias(kotlinCatalog.plugins.ksp)
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

    implementation("com.squareup:kotlinpoet:1.12.0")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")

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
