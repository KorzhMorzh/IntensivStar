package ru.androidschool.intensiv.domain.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.PageResponse

interface FeedRepository {
    fun getPlayingNowMovies(): Observable<PageResponse<Movie>>

    fun getUpcomingMovies(): Observable<PageResponse<Movie>>

    fun getPopularMovies(): Observable<PageResponse<Movie>>
}
