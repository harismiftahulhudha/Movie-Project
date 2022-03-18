package co.harismiftahulhudha.datasource.cache

import co.harismiftahulhudha.domain.Movie

fun Movie_Entity.toMovie(): Movie {
    return Movie(
        id = id,
        backdropPath = backdrop_path ?: "",
        originalLanguage = original_language ?: "",
        originalTitle = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = poster_path ?: "",
        releaseDate = release_date ?: "",
        title = title ?: "",
        voteAverage = vote_average ?: 0.0,
        voteCount = vote_count ?: 0L
    )
}