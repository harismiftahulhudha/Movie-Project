package co.harismiftahulhudha.domain

data class Movie(
    val id: Long = -1,
    val backdropPath: String = "",
    val originalLanguage: String = "",
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long,
)