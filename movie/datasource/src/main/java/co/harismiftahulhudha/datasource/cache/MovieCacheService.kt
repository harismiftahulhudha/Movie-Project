package co.harismiftahulhudha.datasource.cache

import co.harismiftahulhudha.domain.Movie
import com.squareup.sqldelight.db.SqlDriver

interface MovieCacheService {
    suspend fun getPopularMovies(limit: Long, offset: Long): List<Movie>
    suspend fun detailMovie(id: Long): Movie?
    suspend fun insert(movie: Movie)
    suspend fun insert(movies: List<Movie>)

    companion object Factory {
        fun build(sqlDriver: SqlDriver): MovieCacheService {
            return MovieCacheImpl(MovieDatabase(sqlDriver))
        }
        val schema: SqlDriver.Schema = MovieDatabase.Schema
        val dbName: String = "movies.db"
    }
}