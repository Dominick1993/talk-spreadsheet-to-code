package dev.labuda.spreadsheets.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okhttp3.OkHttpClient
import okhttp3.Request

@OptIn(ExperimentalStdlibApi::class)
class ExternalApiClient(private val externalUrl: String) {

    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().build()

    private val threatTypesAdapter = moshi.adapter<List<ApiThreatType>>()

    fun fetchThreatTypes(): List<ApiThreatType> {
        val request = Request.Builder()
            .url(externalUrl)
            .build()

        val threatTypes = client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("Wasn't able to retrieve a response, got a code: ${response.code}")

            threatTypesAdapter.fromJson(
                response.body?.source() ?: error("Response didn't contain any body")
            )
        }

        return threatTypes ?: error("No threat types were fetched, even though they were expected.")
    }
}
