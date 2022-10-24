rootProject.name = "turning-spreadsheets-into-code"
include("generator")
include("external-server")
include("generated-demo")

dependencyResolutionManagement {
    versionCatalogs {
        register("kotlinCatalog") {
            val kotlinVersion = "1.7.20"
            plugin("jvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
            plugin("spring", "org.jetbrains.kotlin.plugin.spring").version(kotlinVersion)
            plugin("ksp", "com.google.devtools.ksp").version("$kotlinVersion-1.0.7")
        }
        register("detektCatalog") {
            val detektVersion = "1.21.0"
            library("formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").version(detektVersion)
            plugin("detekt", "io.gitlab.arturbosch.detekt").version(detektVersion)
        }
    }
}
