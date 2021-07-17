package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.domain.repository.FavouriteMoviesRepository
import ru.androidschool.intensiv.util.setDefaultThreads

class FavouriteMoviesUseCase(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    fun getFavouritesMovies(): Observable<List<Movie>> =
        favouriteMoviesRepository.getFavouritesMovies().setDefaultThreads()

    fun addToFavourites(movieDetailsWithActors: MovieDetailsWithActors) =
        favouriteMoviesRepository.addToFavourite(movieDetailsWithActors)

    fun removeFromFavourites(movieDetailsWithActors: MovieDetailsWithActors) =
        favouriteMoviesRepository.removeFromFavourites(movieDetailsWithActors)
}
