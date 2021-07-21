package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.domain.repository.TVShowsRepository

class TVShowsRepositoryImpl(
    private val api: MovieApiInterface
) : TVShowsRepository {

    override suspend fun getTVShows(): PageResponse<Series> = api.getTvSeries()
}
