package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Observable
import io.reactivex.functions.Function3
import ru.androidschool.intensiv.data.entity.Feed
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.repository.FeedRepositoryImpl
import ru.androidschool.intensiv.domain.repository.FeedRepository
import ru.androidschool.intensiv.util.setDefaultThreads

class FeedUseCase {
    private val repository: FeedRepository by lazy {
        FeedRepositoryImpl
    }

    fun getFeed(): Observable<Feed> = Observable.zip(
        repository.getUpcomingMovies(),
        repository.getPopularMovies(),
        repository.getPlayingNowMovies(),
        Function3<PageResponse<Movie>, PageResponse<Movie>, PageResponse<Movie>, Feed>
        { upcomingMovies, popularMovies, playingNowMovies ->
            Feed(
                upcomingMovies.results,
                playingNowMovies.results,
                popularMovies.results
            )
        })
        .setDefaultThreads()
}
