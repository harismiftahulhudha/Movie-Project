package co.harismiftahulhudha.ui_detail.di

import co.harismiftahulhudha.core.util.Logger
import co.harismiftahulhudha.interactors.GetDetailMovie
import co.harismiftahulhudha.interactors.MovieInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    @Provides
    @Singleton
    @Named("movieDetailLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "MovieDetail",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetDetailMovie(
        interactors: MovieInteractors
    ): GetDetailMovie {
        return interactors.getDetailMovie
    }
}