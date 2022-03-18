package co.harismiftahulhudha.ui_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.harismiftahulhudha.domain.Movie
import coil.ImageLoader
import coil.compose.rememberImagePainter
import co.harismiftahulhudha.ui_list.R
import co.harismiftahulhudha.ui_list.constants.TAG_MOVIE_NAME
import co.harismiftahulhudha.ui_list.constants.TAG_MOVIE_OVERVIEW

@Composable
fun MovieListItemCompose(
    movie: Movie,
    onSelectMovie: (Long) -> Unit,
    imageLoader: ImageLoader
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colors.surface)
            .clickable {
                onSelectMovie(movie.id)
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberImagePainter(
                "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                imageLoader = imageLoader,
                builder = {
                    placeholder(if(isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                }
            )
            Image( // TODO(Replace with Image)
                modifier = Modifier
                    .width(65.dp)
                    .height(100.dp)
                    .background(Color.LightGray)
                ,
                painter = painter,
                contentDescription = movie.originalTitle,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .testTag(TAG_MOVIE_NAME)
                    ,
                    text = movie.title,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .testTag(TAG_MOVIE_OVERVIEW)
                    ,
                    text = movie.overview,
                    style = MaterialTheme.typography.caption,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}