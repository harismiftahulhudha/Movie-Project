package co.harismiftahulhudha.movieproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import co.harismiftahulhudha.movieproject.ui.theme.MovieProjectTheme
import co.harismiftahulhudha.ui_detail.components.MovieDetailCompose
import co.harismiftahulhudha.ui_detail.ui.MovieDetailViewModel
import co.harismiftahulhudha.ui_list.ui.MovieListViewModel
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import co.harismiftahulhudha.ui_list.components.MovieListPagingCompose
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieProjectTheme {
                val navController = rememberAnimatedNavController()
                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = ScreenNavigation.MovieList.route,
                        builder = {
                            addMovieList(navController = navController, imageLoader = imageLoader, width = constraints.maxWidth / 2)
                            addMovieDetail(imageLoader = imageLoader, width = constraints.maxWidth / 2)
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = ScreenNavigation.MovieList.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = {-width},
                animationSpec = tween(
                    durationMillis = 300
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        val viewModel: MovieListViewModel = hiltViewModel()
        MovieListPagingCompose(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            movies = viewModel.movies,
            imageLoader = imageLoader,
            navigateToDetailScreen = { movieId ->
                navController.navigate("${ScreenNavigation.MovieDetail.route}/$movieId")
            }
        )
    }

}

@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieDetail(
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = ScreenNavigation.MovieDetail.route + "/{movieId}",
        arguments = ScreenNavigation.MovieDetail.arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = {width},
                animationSpec = tween(
                    durationMillis = 300
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = {width},
                animationSpec = tween(
                    durationMillis = 300
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) { navBackStackEntry ->
        val viewModel: MovieDetailViewModel = hiltViewModel()
        MovieDetailCompose(state = viewModel.state.value, imageLoader = imageLoader, events = viewModel::onTriggerEvent)
    }
}