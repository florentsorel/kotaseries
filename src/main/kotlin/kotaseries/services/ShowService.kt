package kotaseries.services

import kotaseries.Kotaseries
import kotaseries.exceptions.ShowException
import kotaseries.models.Show
import kotaseries.models.ShowResponse
import kotaseries.models.ShowsResponse
import kotaseries.params.shows.DisplayParams
import kotaseries.params.shows.ListParams
import kotlinx.serialization.json.Json

class ShowService(private val client: Kotaseries, private val json: Json) {
    fun display(params: DisplayParams): Show {
        val body = client.send("GET", "shows/display", params)

        val show = json.decodeFromString(ShowResponse.serializer(), body!!)
        if (show.errors.isNotEmpty()) {
            throw ShowException(show.errors.first().code, show.errors.first().text)
        }

        return show.show!!
    }

    fun list(params: ListParams): List<Show> {
        val body = client.send("GET", "shows/list", params)

        val shows = json.decodeFromString(ShowsResponse.serializer(), body!!)
        if (shows.errors.isNotEmpty()) {
            throw Exception(shows.errors.first().text)
        }

        return shows.shows
    }
}
