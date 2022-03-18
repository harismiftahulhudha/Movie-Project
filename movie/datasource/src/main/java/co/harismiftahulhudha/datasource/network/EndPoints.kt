package co.harismiftahulhudha.datasource.network

object EndPoints {

    private const val BASE_URL = "https://api.themoviedb.org/3"
    const val POPULAR_MOVIES = "$BASE_URL/movie/popular"
    const val SEARCH_MOVIES = "$BASE_URL/search/movie"
    const val DETAIL_MOVIE = "$BASE_URL/movie"
}