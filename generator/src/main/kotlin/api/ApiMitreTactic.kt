package dev.labuda.spreadsheets.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMitreTactic(
    val mitreId: String,
    val name: String,
    val id: Long,
    val url: String,
)
