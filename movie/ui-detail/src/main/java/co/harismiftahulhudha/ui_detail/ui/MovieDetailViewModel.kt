package co.harismiftahulhudha.ui_detail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.harismiftahulhudha.core.domain.DataState
import co.harismiftahulhudha.core.domain.Queue
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.core.util.Logger
import co.harismiftahulhudha.interactors.GetDetailMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getDetailMovie: GetDetailMovie,
    private @Named("movieDetailLogger") val logger: Logger,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val state: MutableState<MovieDetailState> = mutableStateOf(MovieDetailState())

    init {
        savedStateHandle.get<Long>("movieId")?.let { movieId ->
            onTriggerEvent(MovieDetailEvents.GetDetailMovie(movieId))
//            for (i in 1..3) {
//                appendToMessageQueue(UIComponent.Dialog(
//                    title = "Error $i",
//                    description = "Deskripsi error $i"
//                ))
//            }
        }
    }

    fun onTriggerEvent(event: MovieDetailEvents) {
        when (event) {
            is MovieDetailEvents.GetDetailMovie -> {
                getDetailMovie(event.id)
            }
            MovieDetailEvents.OnRemoveQueue -> {
                removeMessage()
            }
        }
    }

    private fun getDetailMovie(id: Long) {
        getDetailMovie.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState, movie = dataState.data)
                }
                is DataState.Success -> {
                    state.value = state.value.copy(movie = dataState.data)
                }
                is DataState.Error -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Unauthorized -> {
                    //
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf()))
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue(mutableListOf()))
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }
}