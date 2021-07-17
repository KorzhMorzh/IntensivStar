package ru.androidschool.intensiv.domain.repository

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors

interface FavouriteMoviesRepository {
    fun getFavouritesMovies(): Observable<List<Movie>>

    fun addToFavourite(movieDetailsWithActors: MovieDetailsWithActors): Disposable

    fun removeFromFavourites(movieDetailsWithActors: MovieDetailsWithActors): Disposable
}
