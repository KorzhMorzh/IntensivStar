package ru.androidschool.intensiv.domain.usecase

import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.domain.repository.TVShowsRepository

class TVShowsUseCase(
    private val repository: TVShowsRepository
) {

    suspend fun getTVShows(): List<Series> =
        repository.getTVShows().results
}
