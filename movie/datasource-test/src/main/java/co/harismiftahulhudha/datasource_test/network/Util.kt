package co.harismiftahulhudha.datasource_test.network

import co.harismiftahulhudha.core.domain.BaseListResponse
import co.harismiftahulhudha.datasource.network.MovieResponse
import co.harismiftahulhudha.datasource.network.toMovie
import co.harismiftahulhudha.domain.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeMovieData(jsonData: String): List<Movie> {
    return json.decodeFromString<BaseListResponse<List<MovieResponse>>>(jsonData).results.map { it.toMovie() }
}