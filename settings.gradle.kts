rootProject.name = "turning-spreadsheets-into-code"
include("generator")

dependencyResolutionManagement {
    versionCatalogs {
        register("detektCatalog") {
            val detektVersion = "1.21.0"
            library("formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").version(detektVersion)
            plugin("detekt", "io.gitlab.arturbosch.detekt").version(detektVersion)
        }
    }
}
