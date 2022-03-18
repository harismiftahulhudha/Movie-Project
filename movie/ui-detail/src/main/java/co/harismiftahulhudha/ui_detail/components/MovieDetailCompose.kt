package co.harismiftahulhudha.ui_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.harismiftahulhudha.components.DefaultScreenUI
import co.harismiftahulhudha.ui_detail.ui.MovieDetailEvents
import co.harismiftahulhudha.ui_detail.ui.MovieDetailState
import coil.ImageLoader
import coil.compose.rememberImagePainter
import co.harismiftahulhudha.ui_detail.R

@Composable
fun MovieDetailCompose(
    state: MovieDetailState,
    events: (MovieDetailEvents) ->  Unit,
    imageLoader: ImageLoader
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(MovieDetailEvents.OnRemoveQueue)
        },
        progressBarState = state.progressBarState
    ) {
        state.movie?.let { movie ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                item {
                    Column {
                        val painter = rememberImagePainter(
                            "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                            imageLoader = imageLoader,
                            builder = {
                                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                            }
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 150.dp),
                            painter = painter,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            text = movie.title,
                            style = MaterialTheme.typography.h4,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                            text = movie.overview,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
            }
        }
    }
}