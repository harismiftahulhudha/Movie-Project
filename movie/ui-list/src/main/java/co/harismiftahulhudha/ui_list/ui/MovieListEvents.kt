package co.harismiftahulhudha.ui_list.ui

sealed class MovieListEvents {
    data class GetPopularMovies(val limit: Long, val offset: Long, val page: Int): MovieListEvents()
    object OnRemoveQueue: MovieListEvents()
}
