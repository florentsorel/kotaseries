package kotaseries

import kotaseries.params.Params
import kotaseries.services.ShowService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class Kotaseries(private val apiKey: String, var token: String? = null) {
    private var client: OkHttpClient
    var shows: ShowService

    companion object {
        const val VERSION = "3.0"
    }

    init {
        val json = Json {
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
        this.client = OkHttpClient()

        this.shows = ShowService(this, json)
    }

    fun send(method: String, path: String, params: Params): String? {
        val request = request(method, path, params)

        val response = response(request)

        return response?.string()
    }

    private fun request(method: String, path: String, params: Params): Request {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.betaseries.com")
            .addPathSegments(path)
            .apply { params.toMap().forEach { (k, v) -> addQueryParameter(k, v) } }
            .build()

        val request = Request.Builder()
            .method(method, null)
            .url(url)
            .header("Content-Type", "application/json")
            .addHeader("X-BetaSeries-Version", VERSION)
            .addHeader("X-BetaSeries-Key", apiKey)
            .addHeader("User-Agent", "kotaseries/$VERSION")
            .apply { token?.let { addHeader("X-BetaSeries-Token", token!!) } }
            .build()

        return request
    }

    private fun response(request: Request): ResponseBody? {
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return null
                }
                return response.body?.string()?.toResponseBody()
            }
        } catch (e: Exception) {
            return null
        }
    }
}
