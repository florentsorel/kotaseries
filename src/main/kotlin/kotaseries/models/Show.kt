package kotaseries.models

import kotaseries.serializers.AliasesSerializer
import kotaseries.serializers.GenresSerializer
import kotaseries.serializers.StringAsListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ShowResponse(
    val show: Show? = null,
    val errors: List<Error>
)

@Serializable
internal data class ShowsResponse(
    val shows: List<Show>,
    val locale: String,
    val errors: List<Error>
)

@Serializable
data class Error(
    val code: Int,
    val text: String
)

//@Serializable
//data class ShowSummary(
//    val id: Int,
//    val thetvdbId: Int,
//    val title: String,
//    val seasons: Int,
//    val episodes: Int,
//    val followers: Int,
//    val creation: String,
//    val poster: String?,
//    val platforms: Show.Platforms?,
//    val seasonsDetails: List<Show.SeasonDetail>,
//    val genres: Map<String, String>,
//)

@Serializable
data class Show(
    val id: Int,
    val thetvdbId: Int,
    val imdbId: String,
    val themoviedbId: Int,
    val slug: String,
    val title: String,
    val originalTitle: String,
    val description: String,
    val seasons: Int,
    val seasonsDetails: List<SeasonDetail>,
    val episodes: Int,
    val followers: Int,
    val comments: Int,
    val similars: Int,
    val characters: Int,
    val creation: String,
    val showrunner: ShowrunnerWithoutSlug?,
    val showrunners: List<Showrunner>,
    @Serializable(with = GenresSerializer::class)
    val genres: Map<String, String>,
    val length: Int,
    val network: String,
    val country: String?,
    val rating: String,
    val status: String, // enum (ended, etc)
    val language: String,
    val notes: Note,
    val inAccount: Boolean,
    val images: Image,
    @Serializable(with = AliasesSerializer::class)
    val aliases: Map<Int, String>,
    val socialLinks: List<SocialLink>,
    val user: User,
    val nextTrailer: String?,
    val nextTrailerHost: String?,
    val resourceUrl: String,
    val platforms: Platforms?,
) {
    @Serializable
    data class SeasonDetail(
        val number: Int,
        val episodes: Int,
    )

    @Serializable
    data class Showrunner(
        val id: Int,
        val name: String,
        val slug: String,
        val picture: String?,
    )

    @Serializable
    data class ShowrunnerWithoutSlug(
        val id: Int,
        val name: String,
        val picture: String?,
    )

    @Serializable
    data class Note(
        val total: Int,
        val mean: Double,
        val user: Int,
    )

    @Serializable
    data class Image(
        val show: String?,
        val banner: String?,
        val box: String?,
        val poster: String?,
        @SerialName("clearlogo")
        val clearLogo: Clearlogo? = null,
    )

    @Serializable
    data class Clearlogo(
        val url: String,
        val width: Int,
        val height: Int,
    )

    @Serializable
    data class SocialLink(
        val type: String,
        val externalId: String,
    )

    @Serializable
    data class User(
        val archived: Boolean,
        val favorited: Boolean,
        val remaining: Int,
        val status: Double,
        val last: String,
        @Serializable(with = StringAsListSerializer::class)
        val tags: List<String>,
        val next: Next,
        val friendsWatching: List<FriendWatching>,
    ) {
        @Serializable
        data class Next(
            val id: Int?,
            val code: String,
            val date: String?,
            val title: String?,
            val image: String?,
        )

        @Serializable
        data class FriendWatching(
            val id: Int,
            val login: String,
            val note: Int?,
            val avatar: String,
        )
    }

    @Serializable
    data class Platforms(
        val forced: List<Svod>? = null,
        val svods: List<Svod>? = null,
        val svod: Svod? = null,
        @SerialName("vod")
        val vods: List<Svod>? = null,
    ) {
        @Serializable
        data class Svod(
            val id: Int,
            val name: String,
            val tag: String? = null,
            val partner: Boolean? = null,
            val color: String,
            val linkUrl: String,
            val available: Available? = null,
            val logo: String?,
        ) {
            @Serializable
            data class Available(
                val last: Int? = null,
                val first: Int? = null,
            )
        }
    }
}
