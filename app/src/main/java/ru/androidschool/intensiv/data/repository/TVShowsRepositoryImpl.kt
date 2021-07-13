package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.domain.repository.TVShowsRepository

object TVShowsRepositoryImpl : TVShowsRepository {
    private val api by lazy {
        MovieApiClient.apiClient
    }

    override fun getTVShows(): Observable<PageResponse<Series>> = api.getTvSeries()
}
