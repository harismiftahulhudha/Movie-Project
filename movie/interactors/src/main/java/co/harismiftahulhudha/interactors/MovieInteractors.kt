package co.harismiftahulhudha.interactors

import co.harismiftahulhudha.datasource.cache.MovieCacheService
import co.harismiftahulhudha.datasource.network.MovieService
import com.squareup.sqldelight.db.SqlDriver

data class MovieInteractors(
    val getPopularMovies: GetPopularMovies,
    val getDetailMovie: GetDetailMovie
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): MovieInteractors {
            val cache = MovieCacheService.build(sqlDriver)
            val service = MovieService.build()
            return MovieInteractors(
                getPopularMovies = GetPopularMovies(
                    cache = cache,
                    service = service
                ),
                getDetailMovie = GetDetailMovie(
                    cache = cache,
                    service = service
                )
            )
        }
        val schema: SqlDriver.Schema = MovieCacheService.schema
        val dbName: String = MovieCacheService.dbName
    }
}
