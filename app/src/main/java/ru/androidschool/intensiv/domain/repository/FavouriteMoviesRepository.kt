package ru.androidschool.intensiv.domain.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors

interface FavouriteMoviesRepository {
    fun getFavouritesMovies(): Observable<List<Movie>>

    fun addToFavourite(movieDetailsWithActors: MovieDetailsWithActors)

    fun removeFromFavourites(movieDetailsWithActors: MovieDetailsWithActors)
}
