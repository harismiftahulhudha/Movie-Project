package co.harismiftahulhudha.ui_list.di

import co.harismiftahulhudha.core.util.Logger
import co.harismiftahulhudha.interactors.GetPopularMovies
import co.harismiftahulhudha.interactors.MovieInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieListModule {

    @Provides
    @Singleton
    @Named("movieListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "MovieList",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetPopularMovies(
        interactors: MovieInteractors
    ): GetPopularMovies {
        return interactors.getPopularMovies
    }
}