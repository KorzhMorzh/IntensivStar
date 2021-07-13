package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.data.repository.TVShowsRepositoryImpl
import ru.androidschool.intensiv.util.setDefaultThreads

class TVShowsUseCase {
    private val repository by lazy {
        TVShowsRepositoryImpl
    }

    fun getTVShows(): Observable<List<Series>> =
        repository.getTVShows().map { it.results }.setDefaultThreads()
}
