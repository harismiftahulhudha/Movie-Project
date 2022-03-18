package co.harismiftahulhudha.interactors

import co.harismiftahulhudha.core.domain.DataState
import co.harismiftahulhudha.core.domain.ProgressBarState
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.datasource.cache.MovieCacheService
import co.harismiftahulhudha.datasource.network.MovieService
import co.harismiftahulhudha.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMovies(
    private val cache: MovieCacheService,
    private val service: MovieService
) {
    fun execute(limit: Long, offset: Long, page: Int): Flow<DataState<List<Movie>>> = flow {
        var cachedHeros = cache.getPopularMovies(limit, offset)

        try {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading, data = cachedHeros))

            val movies: List<Movie> = try {
                service.getPopularMovies(page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    ),
                    data = cachedHeros
                ))
                listOf()
            }
            cache.insert(movies)

            cachedHeros = cache.getPopularMovies(limit, offset)
            emit(DataState.Success(cachedHeros))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Error(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    ),
                    data = cachedHeros
                )
            )
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle, data = cachedHeros))
        }
    }

    suspend fun executeNotFlow(limit: Long, page: Int): DataState<List<Movie>> {
        val offset = if (page <= 1) {
            0L
        } else {
            (page - 1).toLong() * limit
        }
        var cachedHeros = cache.getPopularMovies(limit, offset)

        try {

            val movies: List<Movie> = try {
                service.getPopularMovies(page)
            } catch (e: Exception) {
                e.printStackTrace()
                // hanya untuk unit test
//                return DataState.Error(
//                    uiComponent = UIComponent.Dialog(
//                        title = "Error",
//                        description = e.message ?: "Unknown Error"
//                    ),
//                    data = cachedHeros
//                )
                listOf()
            }
            cache.insert(movies)

            cachedHeros = cache.getPopularMovies(limit, offset)
            return DataState.Success(cachedHeros)
        } catch (e: Exception) {
            e.printStackTrace()
            return DataState.Error(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message ?: "Unknown Error"
                ),
                data = cachedHeros
            )
        }
    }
}