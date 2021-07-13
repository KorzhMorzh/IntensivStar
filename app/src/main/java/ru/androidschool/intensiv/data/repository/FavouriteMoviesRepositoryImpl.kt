package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.data.local.MovieDatabase
import ru.androidschool.intensiv.data.mappers.convertMovieEntity
import ru.androidschool.intensiv.domain.repository.FavouriteMoviesRepository
import ru.androidschool.intensiv.presentation.MovieFinderApp

object FavouriteMoviesRepositoryImpl : FavouriteMoviesRepository {
    private val dao by lazy {
        MovieDatabase.get(MovieFinderApp.instance?.applicationContext!!).movieDao()
    }

    override fun getFavouritesMovies(): Observable<List<Movie>> = dao.getMovies()

    override fun addToFavourite(movieDetailsWithActors: MovieDetailsWithActors) {
        dao.save(convertMovieEntity(movieDetailsWithActors))
    }

    override fun removeFromFavourites(movieDetailsWithActors: MovieDetailsWithActors) {
        dao.delete(convertMovieEntity(movieDetailsWithActors))
    }
}
