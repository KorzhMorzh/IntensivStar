package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.MovieCredits
import ru.androidschool.intensiv.data.entity.MovieDetails
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.data.local.MovieDatabase
import ru.androidschool.intensiv.data.mappers.convertToMovie
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.presentation.MovieFinderApp

object MovieDetailsRepositoryImpl : MovieDetailsRepository {
    private val api by lazy {
        MovieApiClient.apiClient
    }

    private val dao by lazy {
        MovieDatabase.get(MovieFinderApp.instance?.applicationContext!!).movieDao()
    }

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
