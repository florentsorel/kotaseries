package kotaseries.params.shows

import kotaseries.enums.Locale
import kotaseries.mapOfNotNullValues
import kotaseries.params.Params
import kotlinx.serialization.Serializable

@Serializable
data class DisplayParams(
    val id: Int? = null,
    val thetvdbId: Int? = null,
    val imdbId: String? = null,
    val summary: Boolean? = null,
    val slug: String? = null,
    val locale: Locale? = null,
): Params {
    override fun toMap(): Map<String, String?> {
        return mapOfNotNullValues(
            "id" to id,
            "thetvdb_id" to thetvdbId,
            "imdb_id" to imdbId,
            "summary" to summary,
            "url" to slug,
            "locale" to locale?.value,
        )
    }
}
