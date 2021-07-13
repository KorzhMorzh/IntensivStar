package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.androidschool.intensiv.data.entity.MovieCredits
import ru.androidschool.intensiv.data.entity.MovieDetails
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.data.repository.MovieDetailsRepositoryImpl
import ru.androidschool.intensiv.domain.repository.MovieDetailsRepository
import ru.androidschool.intensiv.util.setDefaultThreads

class MovieDetailsUseCase {
    private val repository: MovieDetailsRepository by lazy {
        MovieDetailsRepositoryImpl
    }

    fun getMovieDetails(movieId: Int): Observable<MovieDetailsWithActors> {
        val remoteObservable = Observable
            .zip(
                repository.getMovieDetails(movieId),
                repository.getMovieCredits(movieId),
                BiFunction<MovieDetails, MovieCredits, MovieDetailsWithActors> { movie, credits ->
                    MovieDetailsWithActors(
                        movie,
                        credits.cast
                    )
                }
            )
        return repository
            .getMovieDetailsFromLocal(movieId)
            .onErrorResumeNext(remoteObservable)
            .switchIfEmpty(remoteObservable)
            .setDefaultThreads()
    }
}
