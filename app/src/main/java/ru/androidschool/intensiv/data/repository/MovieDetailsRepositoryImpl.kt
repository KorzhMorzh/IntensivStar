package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.MovieCredits
import ru.androidschool.intensiv.data.entity.MovieDetails
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.data.local.MovieDao
import ru.androidschool.intensiv.data.mappers.convertToMovie
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val api: MovieApiInterface,
    private val dao: MovieDao
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int): Observable<MovieDetails> {
        return api.getMovieDetails(movieId)
    }

    override fun getMovieCredits(movieId: Int): Observable<MovieCredits> {
        return api.getMovieCredits(movieId)
    }

    override fun getMovieDetailsFromLocal(movieId: Int): Observable<MovieDetailsWithActors> {
        return dao.getMovie(movieId).map { convertToMovie(it) }.toObservable()
    }
}
