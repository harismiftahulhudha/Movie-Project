package co.harismiftahulhudha.datasource.network

import co.harismiftahulhudha.domain.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null ,
    @SerialName("original_title")
    val originalTitle: String? = null ,
    @SerialName("overview")
    val overview: String? = null ,
    @SerialName("popularity")
    val popularity: Double? = null ,
    @SerialName("poster_path")
    val posterPath: String? = null ,
    @SerialName("release_date")
    val releaseDate: String? = null ,
    @SerialName("title")
    val title: String? = null ,
    @SerialName("vote_average")
    val voteAverage: Double? = null ,
    @SerialName("vote_count")
    val voteCount: Long? = null ,
)

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0L
    )
}