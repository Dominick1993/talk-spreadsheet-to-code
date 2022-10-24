package dev.labuda.spreadsheets.file

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.labuda.spreadsheets.KotlinPoetUtils.addWhenStatement
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

object OurThreatTypeMapping {
    // Google sheet with mapping exported in XLSX format
    private const val MAPPING_FILE_PATH = "turningSpreadsheetsIntoCode.xlsx"

    // Indexing starts from 1
    private const val MAPPING_SHEET_NUMBER = 1

    private const val THREAT_NAME_COLUMN = 'E'
    private const val THREAT_TYPE_ID_COLUMN = 'D'
    private const val THREAT_DESCRIPTION_COLUMN = 'H'
    private const val THREAT_INVESTIGATION_COLUMN = 'I'
    private const val THREAT_REMEDIATION_COLUMN = 'J'
    private const val THREAT_DETECTION_TYPE_COLUMN = 'G'

    data class ThreatTypeMapping(
        val threatTypeId: Long,
        val threatName: String,
        val description: String,
        val recommendedInvestigation: String,
        val remediationSteps: String,
        val detectionType: String,
    )

    private val mapping = run {
        val file = FileInputStream(File(MAPPING_FILE_PATH))
        val workbook = XSSFWorkbook(file)

        val sheet = workbook.getSheetAt(MAPPING_SHEET_NUMBER - 1)

        val mapping = mutableListOf<ThreatTypeMapping>()

        sheet.rowIterator().forEach { row ->
            val cell = row.getCell(THREAT_TYPE_ID_COLUMN.alphabetOrder())
            if (cell.cellType == CellType.NUMERIC) {
                val nameColumn = row.getCell(THREAT_NAME_COLUMN.alphabetOrder())
                val descriptionColumn = row.getCell(THREAT_DESCRIPTION_COLUMN.alphabetOrder())
                val investigationColumn = row.getCell(THREAT_INVESTIGATION_COLUMN.alphabetOrder())
                val remediationColumn = row.getCell(THREAT_REMEDIATION_COLUMN.alphabetOrder())
                val detectionTypeColumn = row.getCell(THREAT_DETECTION_TYPE_COLUMN.alphabetOrder())

                mapping.add(
                    ThreatTypeMapping(
                        cell.numericCellValue.toLong(),
                        nameColumn.stringCellValue,
                        descriptionColumn.stringCellValue,
                        investigationColumn.stringCellValue,
                        remediationColumn.stringCellValue,
                        detectionTypeColumn.stringCellValue
                    )
                )
            }
        }

        mapping.toList()
    }

    val detectionType = TypeSpec.enumBuilder("DetectionType")
        .addEnumConstant("STATIC_RISK_INDICATOR")
        .addEnumConstant("BEHAVIORAL_INDICATOR")
        .build()

    fun getDescriptionFunction() =
        FunSpec.builder("getDescription")
            .returns(String::class)
            .addThreatMappings(mapping, "%S", ThreatTypeMapping::description)
            .build()

    fun getNameFunction() =
        FunSpec.builder("getName")
            .returns(String::class)
            .addThreatMappings(mapping, "%S", ThreatTypeMapping::threatName)
            .build()

    fun getRecommendedInvestigationFunction() =
        FunSpec.builder("getRecommendedInvestigation")
            .returns(String::class)
            .addThreatMappings(mapping, "%S", ThreatTypeMapping::recommendedInvestigation)
            .build()

    fun getRemediationStepsFunction() =
        FunSpec.builder("getRemediationSteps")
            .returns(String::class)
            .addThreatMappings(mapping, "%S", ThreatTypeMapping::remediationSteps)
            .build()

    fun getDetectionTypeFunction() =
        FunSpec.builder("getDetectionType")
            .addThreatMappings(mapping, "%L") {
                when (it.detectionType) {
                    "Static risk indicator" -> "DetectionType.STATIC_RISK_INDICATOR"
                    "Behavioral indicator" -> "DetectionType.BEHAVIORAL_INDICATOR"
                    else -> error(
                        "Unknown detection type: ${it.detectionType}"
                    )
                }
            }
            .build()

    /**
     * Indexed from 0.
     */
    private fun Char.alphabetOrder() =
        this.lowercaseChar().code - 'a'.code

    private fun FunSpec.Builder.addThreatMappings(
        threatTypes: List<ThreatTypeMapping>,
        statementFormat: String,
        kFunction1: (ThreatTypeMapping) -> Any?,
    ) =
        this.addWhenStatement<Long>("threatTypeId") { funSpec ->
            threatTypes.forEach {
                funSpec.beginControlFlow("%LL ->", it.threatTypeId)
                funSpec.addStatement(statementFormat, kFunction1(it) ?: error("provided lambda result was null"))
                funSpec.endControlFlow()
            }
        }
}
