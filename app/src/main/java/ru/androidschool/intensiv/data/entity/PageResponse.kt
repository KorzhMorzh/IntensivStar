package ru.androidschool.intensiv.data.entity

import com.google.gson.annotations.SerializedName

data class PageResponse<T>(
    var page: Int,
    var results: List<T>,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)
