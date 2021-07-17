package ru.androidschool.intensiv.presentation.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.local.MovieDao
import ru.androidschool.intensiv.data.local.MovieDatabase
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.repository.FavouriteMoviesRepositoryImpl
import ru.androidschool.intensiv.data.repository.MovieDetailsRepositoryImpl
import ru.androidschool.intensiv.domain.repository.FavouriteMoviesRepository
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.presentation.MovieFinderApp
import javax.inject.Singleton


@Module
class AppModule(val appContext: MovieFinderApp) {
    @Provides
    @Singleton
    fun provideApp(): Application = appContext

    @Provides
    @Singleton
    fun provideApi(): MovieApiInterface = MovieApiClient.apiClient

    @Singleton
    @Provides
    fun provideDb(app: Application): MovieDatabase {
        return MovieDatabase.get(app)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailsRepository(
        dao: MovieDao,
        api: MovieApiInterface
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(api, dao)
    }

    @Singleton
    @Provides
    fun provideFavouriteMoviesRepository(
        dao: MovieDao
    ): FavouriteMoviesRepository {
        return FavouriteMoviesRepositoryImpl(dao)
    }

}