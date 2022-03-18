package co.harismiftahulhudha.datasource_test.cache

import co.harismiftahulhudha.domain.Movie

class MovieDatabaseFake {
    val movies: MutableList<Movie> = mutableListOf()
}