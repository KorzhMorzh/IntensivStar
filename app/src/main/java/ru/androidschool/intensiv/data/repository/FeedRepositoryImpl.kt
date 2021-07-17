package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val api: MovieApiInterface
) : FeedRepository {

    override fun getPlayingNowMovies(): Observable<PageResponse<Movie>> = api.getPlayingNowMovies()

    override fun getUpcomingMovies(): Observable<PageResponse<Movie>> = api.getUpcomingMovies()

    override fun getPopularMovies(): Observable<PageResponse<Movie>> = api.getPopularMovies()
}
