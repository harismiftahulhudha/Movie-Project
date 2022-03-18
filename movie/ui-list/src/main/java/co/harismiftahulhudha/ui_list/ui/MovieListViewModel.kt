package co.harismiftahulhudha.ui_list.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.harismiftahulhudha.core.domain.DataState
import co.harismiftahulhudha.core.domain.Queue
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.core.util.Logger
import co.harismiftahulhudha.domain.Movie
import co.harismiftahulhudha.interactors.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private @Named("movieListLogger") val logger: Logger,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val state: MutableState<MovieListState> = mutableStateOf(MovieListState())

    init {
        // nyalakan ini ketika tidak memakai paging tapi belum terintegrasi pagination
//        onTriggerEvent(MovieListEvents.GetPopularMovies(limit = limit.toLong(), offset = offset.toLong(), page = page.toInt()))
//        for (i in 1..3) {
//            appendToMessageQueue(UIComponent.Dialog(
//                title = "Error $i",
//                description = "Deskripsi error $i"
//            ))
//        }
    }

    fun onTriggerEvent(events: MovieListEvents) {
        when (events) {
            is MovieListEvents.GetPopularMovies -> {
                getPopularMovies(events.limit, events.offset, events.page)
            }
            MovieListEvents.OnRemoveQueue -> {
                removeMessage()
            }
        }
    }

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(
            limit = 20L,
            getPopularMovies = getPopularMovies,
            logger = logger,
            appendToMessageQueue = {
                appendToMessageQueue(it)
            }
        )
    }.flow.cachedIn(viewModelScope)

    private fun getPopularMovies(limit: Long, offset: Long, page: Int) {
        getPopularMovies.execute(limit = limit, offset = offset, page = page).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState, movies = dataState.data ?: listOf())
                }
                is DataState.Success -> {
                    state.value = state.value.copy(movies = dataState.data ?: listOf())
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