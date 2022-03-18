package co.harismiftahulhudha.ui_list.ui

import co.harismiftahulhudha.core.domain.ProgressBarState
import co.harismiftahulhudha.core.domain.Queue
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.domain.Movie

data class MovieListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val movies: List<Movie> = listOf(),
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
