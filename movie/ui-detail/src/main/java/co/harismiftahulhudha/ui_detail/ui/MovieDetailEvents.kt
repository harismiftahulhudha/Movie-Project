package co.harismiftahulhudha.ui_detail.ui


sealed class MovieDetailEvents {
    data class GetDetailMovie(
        val id: Long
    ): MovieDetailEvents()
    object OnRemoveQueue: MovieDetailEvents()
}
