package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.domain.repository.FeedRepository

object FeedRepositoryImpl : FeedRepository {
    private val api by lazy {
        MovieApiClient.apiClient
    }

    override fun getPlayingNowMovies(): Observable<PageResponse<Movie>> = api.getPlayingNowMovies()

    override fun getUpcomingMovies(): Observable<PageResponse<Movie>> = api.getUpcomingMovies()

    override fun getPopularMovies(): Observable<PageResponse<Movie>> = api.getPopularMovies()
}
