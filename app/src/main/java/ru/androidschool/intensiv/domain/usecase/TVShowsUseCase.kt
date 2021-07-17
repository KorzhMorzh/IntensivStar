package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.domain.repository.TVShowsRepository
import ru.androidschool.intensiv.util.setDefaultThreads

class TVShowsUseCase(
    private val repository: TVShowsRepository
) {

    fun getTVShows(): Observable<List<Series>> =
        repository.getTVShows().map { it.results }.setDefaultThreads()
}
