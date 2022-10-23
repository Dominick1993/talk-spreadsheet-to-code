rootProject.name = "turning-spreadsheets-into-code"
include("generator")
include("external-server")

dependencyResolutionManagement {
    versionCatalogs {
        register("kotlinCatalog") {
            val kotlinVersion = "1.7.20"
            plugin("jvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
            plugin("spring", "org.jetbrains.kotlin.plugin.spring").version(kotlinVersion)
        }
        register("detektCatalog") {
            val detektVersion = "1.21.0"
            library("formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").version(detektVersion)
            plugin("detekt", "io.gitlab.arturbosch.detekt").version(detektVersion)
        }
    }
}
