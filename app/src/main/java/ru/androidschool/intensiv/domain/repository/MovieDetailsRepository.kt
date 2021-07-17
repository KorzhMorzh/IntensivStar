package ru.androidschool.intensiv.domain.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.MovieCredits
import ru.androidschool.intensiv.data.entity.MovieDetails
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors

interface MovieDetailsRepository {
    fun getMovieDetails(movieId: Int): Observable<MovieDetails>

    fun getMovieCredits(movieId: Int): Observable<MovieCredits>

    fun getMovieDetailsFromLocal(movieId: Int): Observable<MovieDetailsWithActors>
}
