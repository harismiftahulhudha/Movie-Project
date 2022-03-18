package co.harismiftahulhudha.movieproject.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenNavigation(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object MovieList: ScreenNavigation(
        route = "movieList",
        arguments = emptyList()
    )

    object MovieDetail: ScreenNavigation(
        route = "movieDetail",
        arguments = listOf(
            navArgument("movieId") {
                type = NavType.LongType
            }
        )
    )

}
