package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.TVShowsRepository

class TVShowsRepositoryImpl(
    private val api: MovieApiInterface
) : TVShowsRepository {

    override fun getTVShows(): Observable<PageResponse<Series>> = api.getTvSeries()
}
