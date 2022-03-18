package co.harismiftahulhudha.movieproject.di

import android.app.Application
import co.harismiftahulhudha.interactors.MovieInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieInteractorsModule {

    @Provides
    @Singleton
    @Named("movieAndroidSqlDriver") // in case you had another SQL Delight db
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            MovieInteractors.schema,
            app,
            MovieInteractors.dbName
        )
    }

    @Provides
    @Singleton
    fun provideHeroInteractors(
        @Named("movieAndroidSqlDriver") sqlDriver: SqlDriver
    ): MovieInteractors {
        return MovieInteractors.build(sqlDriver = sqlDriver)
    }

}