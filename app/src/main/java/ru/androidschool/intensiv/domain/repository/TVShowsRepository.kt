package ru.androidschool.intensiv.domain.repository

import ru.androidschool.intensiv.data.entity.PageResponse
import ru.androidschool.intensiv.data.entity.Series

interface TVShowsRepository {
    suspend fun getTVShows(): PageResponse<Series>
}
