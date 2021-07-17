package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.data.local.MovieDao
import ru.androidschool.intensiv.data.mappers.convertMovieEntity
import ru.androidschool.intensiv.domain.repository.FavouriteMoviesRepository
import javax.inject.Inject

class FavouriteMoviesRepositoryImpl @Inject constructor(
    private val dao: MovieDao
) : FavouriteMoviesRepository {

    override fun getFavouritesMovies(): Observable<List<Movie>> = dao.getMovies()

    override fun addToFavourite(movieDetailsWithActors: MovieDetailsWithActors): Disposable {
        return dao.save(convertMovieEntity(movieDetailsWithActors))
    }

    override fun removeFromFavourites(movieDetailsWithActors: MovieDetailsWithActors): Disposable {
        return dao.delete(convertMovieEntity(movieDetailsWithActors))
    }
}
