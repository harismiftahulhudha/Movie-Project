package co.harismiftahulhudha.ui_list.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.harismiftahulhudha.core.domain.DataState
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.core.util.Logger
import co.harismiftahulhudha.domain.Movie
import co.harismiftahulhudha.interactors.GetPopularMovies
import coil.network.HttpException
import java.io.IOException


class MoviePagingSource(
    private val limit: Long,
    private val getPopularMovies: GetPopularMovies,
    private val logger: Logger,
    private val appendToMessageQueue: (UIComponent) -> Unit
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val dataState = getPopularMovies.executeNotFlow(
                limit = limit, page = page
            )
            when(dataState) {
                is DataState.Loading -> {
                    LoadResult.Error(Exception())
                }
                is DataState.Success -> {
                    val movies = dataState.data as List<Movie>
                    LoadResult.Page(
                        data = movies,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (movies.isEmpty() || movies.size != 20) null else page + 1
                    )
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
                    LoadResult.Error(Exception())
                }
                is DataState.Unauthorized -> {
                    LoadResult.Error(Exception())
                }
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}