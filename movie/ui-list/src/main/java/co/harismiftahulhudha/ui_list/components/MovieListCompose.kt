package co.harismiftahulhudha.ui_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import co.harismiftahulhudha.components.CircularIndeterminateProgressBar
import co.harismiftahulhudha.components.DefaultScreenUI
import co.harismiftahulhudha.core.domain.ProgressBarState
import co.harismiftahulhudha.domain.Movie
import co.harismiftahulhudha.ui_list.ui.MovieListEvents
import co.harismiftahulhudha.ui_list.ui.MovieListState
import coil.ImageLoader
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieListCompose(
    state: MovieListState,
    events: (MovieListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Long) -> Unit
) {
    LazyColumn {
        items(state.movies) { movie ->
            MovieListItemCompose(
                movie = movie,
                onSelectMovie = { movieId ->
                    navigateToDetailScreen(movieId)
                },
                imageLoader = imageLoader

            )
        }
    }
    if (state.progressBarState is ProgressBarState.Loading && state.movies.isEmpty()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MovieListPagingCompose(
    state: MovieListState,
    events: (MovieListEvents) -> Unit,
    movies: Flow<PagingData<Movie>>,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Long) -> Unit
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(MovieListEvents.OnRemoveQueue)
        }
    ) {
        val dataMovies = movies.collectAsLazyPagingItems()
        LazyColumn {
            items(dataMovies) { movie ->
                movie?.let {
                    MovieListItemCompose(
                        movie = movie,
                        onSelectMovie = { movieId ->
                            navigateToDetailScreen(movieId)
                        },
                        imageLoader = imageLoader

                    )
                }
            }
        }
        dataMovies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    CircularIndeterminateProgressBar()
                }
                loadState.append is LoadState.Loading -> {
                    //You can add component to manage load state when next response page is loading
                }
                loadState.refresh is LoadState.Error -> {

                }
                loadState.append is LoadState.Error -> {
                    //You can add component to show error message
                }
            }
        }
    }
}