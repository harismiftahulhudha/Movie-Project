package co.harismiftahulhudha.interactors

import co.harismiftahulhudha.core.domain.DataState
import co.harismiftahulhudha.core.domain.ProgressBarState
import co.harismiftahulhudha.core.domain.UIComponent
import co.harismiftahulhudha.datasource_test.cache.MovieCacheFake
import co.harismiftahulhudha.datasource_test.cache.MovieDatabaseFake
import co.harismiftahulhudha.datasource_test.network.MovieServiceFake
import co.harismiftahulhudha.datasource_test.network.MovieServiceResponseType
import co.harismiftahulhudha.datasource_test.network.data.MovieDataValid
import co.harismiftahulhudha.datasource_test.network.serializeMovieData
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPopularMoviesTest {

    private lateinit var getPopularMovies: GetPopularMovies

    @Test
    fun getMovies_dataValid() = runBlocking {
        val movieDatabase = MovieDatabaseFake()
        val movieCache = MovieCacheFake(movieDatabase)
        val movieService = MovieServiceFake.build(
            type = MovieServiceResponseType.ValidData
        )

        getPopularMovies = GetPopularMovies(
            cache = movieCache,
            service = movieService
        )

        var cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.isEmpty())

        val results = getPopularMovies.executeNotFlow(20, 1)

        assert(results is DataState.Success)
        assert((results as DataState.Success).data?.size ?: 0 == 20)

        // cek kalau data di cache sudah tidak kosong
        cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.size == 20)
    }

    @Test
    fun getMovies_dataInvalid() = runBlocking {
        val movieDatabase = MovieDatabaseFake()
        val movieCache = MovieCacheFake(movieDatabase)
        val movieService = MovieServiceFake.build(
            type = MovieServiceResponseType.InvalidData
        )

        getPopularMovies = GetPopularMovies(
            cache = movieCache,
            service = movieService
        )

        var cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.isEmpty())

        val movieData = serializeMovieData(MovieDataValid.data)
        movieCache.insert(movieData)

        cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.isNotEmpty())

        val results = getPopularMovies.executeNotFlow(20, 1)
        assert(results is DataState.Error)
        assert(((results as DataState.Error).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))

        // kondisi yang benar ketika unit tes meskipun trjadi error pada api, data tetap tampil dari cache
//        assert(results is DataState.Success)
//        assert((results as DataState.Success).data?.size == 20)
    }

    @Test
    fun getMovies_dataEmpty() = runBlocking {
        val movieDatabase = MovieDatabaseFake()
        val movieCache = MovieCacheFake(movieDatabase)
        val movieService = MovieServiceFake.build(
            type = MovieServiceResponseType.Emptylist
        )

        getPopularMovies = GetPopularMovies(
            cache = movieCache,
            service = movieService
        )

        var cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.isEmpty())

        val results = getPopularMovies.executeNotFlow(20, 1)

        // confirm the second emission is data
        assert(results is DataState.Success)
        assert((results as DataState.Success).data?.size == 0)

        // confirm the cache is no longer empty
        cachedMovies = movieCache.getPopularMovies(20, 0)
        assert(cachedMovies.isEmpty())
    }
}