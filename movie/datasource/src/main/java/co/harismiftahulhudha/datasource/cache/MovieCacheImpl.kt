package co.harismiftahulhudha.datasource.cache

import co.harismiftahulhudha.domain.Movie

class MovieCacheImpl(
    database: MovieDatabase
): MovieCacheService {

    private var queries: MovieDBQueries = database.movieDBQueries

    override suspend fun getPopularMovies(limit: Long, offset: Long): List<Movie> {
        return queries.selectAll(limit, offset).executeAsList().map { it.toMovie() }
    }

    override suspend fun detailMovie(id: Long): Movie {
        return queries.detailMovie(id).executeAsOne().toMovie()
    }

    override suspend fun insert(movie: Movie) {
        return movie.run {
            queries.insertMovie(
                id = id,
                backdrop_path = backdropPath,
                original_language = originalLanguage,
                original_title = originalTitle,
                overview = overview,
                popularity = popularity,
                poster_path = posterPath,
                release_date = releaseDate,
                title = title,
                vote_average = voteAverage,
                vote_count = voteCount
            )
        }
    }

    override suspend fun insert(movies: List<Movie>) {
        movies.forEach {
            try {
                insert(it)
            } catch (e: Exception) {
                e.printStackTrace()
                // if one has an error just continue with others
            }
        }
    }
}
