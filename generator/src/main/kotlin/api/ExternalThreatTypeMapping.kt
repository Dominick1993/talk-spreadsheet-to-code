package dev.labuda.spreadsheets.api

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import dev.labuda.spreadsheets.KotlinPoetUtils.createDataClassWithPrimaryConstructorFields
import dev.labuda.spreadsheets.KotlinPoetUtils.addWhenStatement

class ExternalThreatTypeMapping {

    private val defaultExternalUrl = "http://localhost:8080/"

    private val mitreTacticClassName = ClassName("", "MitreTactic")

    // MitreTactic data class containing all of its fields in primary constructor
    val mitreTacticType = createDataClassWithPrimaryConstructorFields(
        mitreTacticClassName,
        listOf(
            "mitreId" to String::class,
            "name" to String::class,
            "id" to Long::class,
            "url" to String::class
        )
    )

    private val threatTypes = ExternalApiClient(defaultExternalUrl).fetchThreatTypes()

    fun getMitreTacticsFunction() =
        FunSpec.builder("getMitreTactics")
            .addThreatMappings(threatTypes, "%L") {
                "listOf(${it.mitreTactics.joinToString(",\n") { mitreTactic ->
                    createMitreTacticInstance(mitreTactic)
                }})"
            }
            .build()

    private fun createMitreTacticInstance(mitreTactic: ApiMitreTactic): String =
        """${mitreTacticClassName.simpleName}(
        |   "${mitreTactic.mitreId}", "${mitreTactic.name}", ${mitreTactic.id}, "${mitreTactic.url}"
        |)""".trimMargin()

    private fun FunSpec.Builder.addThreatMappings(
        threatTypes: List<ApiThreatType>,
        statementFormat: String,
        kFunction1: (ApiThreatType) -> Any?,
    ) =
        this.addWhenStatement<Long>("threatTypeId") { funSpec ->
            threatTypes.forEach {
                funSpec.beginControlFlow("%LL ->", it.id)
                funSpec.addStatement(statementFormat, kFunction1(it) ?: error("provided lambda result was null"))
                funSpec.endControlFlow()
            }
        }
}
