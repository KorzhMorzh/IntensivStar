package ru.androidschool.intensiv.domain.repository

import io.reactivex.Observable
import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.entity.Series

interface TVShowsRepository {
    fun getTVShows(): Observable<PageResponse<Series>>
}
