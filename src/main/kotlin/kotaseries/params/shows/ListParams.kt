package kotaseries.params.shows

import kotaseries.enums.Locale
import kotaseries.mapOfNotNullValues
import kotaseries.params.Params
import kotaseries.toTimestamp
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ListParams(
   val order: Order? = null,
   val since: Instant? = null,
   val recent: Boolean? = null,
   val starting: String? = null,
   val start: Int? = null,
   val limit: Int? = null,
   val platforms: List<Int>? = null,
   val country: String? = null,
   val summary: Boolean? = null,
   val locale: Locale? = null,
) : Params {
   enum class Order(val value: String) {
      ALPHABETICAL("alphabetical"),
      FOLLOWERS("followers"),
      POPULARITY("popularity"),
   }

   override fun toMap(): Map<String, String?> {
      return mapOfNotNullValues(
         "order" to order?.value,
         "since" to since?.toTimestamp(),
         "recent" to recent,
         "starting" to starting,
         "start" to start,
         "limit" to limit,
         "platforms" to platforms?.joinToString(","),
         "country" to country,
         "summary" to summary,
         "locale" to locale?.value,
      )
   }
}
