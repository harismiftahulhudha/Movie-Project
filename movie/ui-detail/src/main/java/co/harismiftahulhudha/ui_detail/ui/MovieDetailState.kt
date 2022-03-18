package co.harismiftahulhudha.ui_detail.ui

import co.harismiftahulhudha.core.domain.ProgressBarState
import co.harismiftahulhudha.core.domain.Queue
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.domain.Movie

data class MovieDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val movie: Movie? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)