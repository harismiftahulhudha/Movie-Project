package co.harismiftahulhudha.datasource_test.cache

import co.harismiftahulhudha.datasource.cache.MovieCacheService
import co.harismiftahulhudha.domain.Movie

class MovieCacheFake(
    private val db: MovieDatabaseFake
): MovieCacheService {
    override suspend fun getPopularMovies(limit: Long, offset: Long): List<Movie> {
        return db.movies
    }

    override suspend fun detailMovie(id: Long): Movie? {
        return db.movies.find { it.id == id }
    }

    override suspend fun insert(movie: Movie) {
        if (db.movies.isNotEmpty()) {
            var isInsertNewItem = false
            for (i in db.movies) {
                if (i.id == movie.id) {
                    db.movies.remove(i)
                    db.movies.add(movie)
                    isInsertNewItem = true
                    break
                }
            }
            if (!isInsertNewItem) {
                db.movies.add(movie)
            }
        } else {
            db.movies.add(movie)
        }
    }

    override suspend fun insert(movies: List<Movie>) {
        if (db.movies.isNotEmpty()){
            for(hero in movies){
                if(db.movies.contains(hero)){
                    db.movies.remove(hero)
                    db.movies.add(hero)
                }
            }
        } else{
            db.movies.addAll(movies)
        }
    }

}