package co.harismiftahulhudha.datasource.network

import co.harismiftahulhudha.core.domain.BaseListResponse
import co.harismiftahulhudha.domain.Movie
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class MovieServiceImpl(
    private val httpClient: HttpClient
): MovieService {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return httpClient.get<BaseListResponse<List<MovieResponse>>> {
            url(EndPoints.POPULAR_MOVIES)
            parameter("page", page)
            headers {
                append(HttpHeaders.Authorization, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGFjZDhhYzNlMjc0MTYyYTQzNmZiNTQyOWM1ODIyZiIsInN1YiI6IjYyMmUyNzI1YWFkOWMyMDA3OTAxNGY0NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YzIjd-yu19sh_b1uS_UUB5gY6vFn90RAgi5HOoP9T4g")
            }
        }.results.map { it.toMovie() }
    }

    override suspend fun detailMovie(id: Long): Movie {
        return httpClient.get<MovieResponse> {
            url("${EndPoints.DETAIL_MOVIE}/$id")
            headers {
                append(HttpHeaders.Authorization, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGFjZDhhYzNlMjc0MTYyYTQzNmZiNTQyOWM1ODIyZiIsInN1YiI6IjYyMmUyNzI1YWFkOWMyMDA3OTAxNGY0NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YzIjd-yu19sh_b1uS_UUB5gY6vFn90RAgi5HOoP9T4g")
            }
        }.toMovie()
    }
}