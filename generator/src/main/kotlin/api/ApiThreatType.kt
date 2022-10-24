package dev.labuda.spreadsheets.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiThreatType(
    val id: String,
    val mitreTactics: List<ApiMitreTactic>,
)
