package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"

    val rating: Float
        get() = voteAverage?.div(2)?.toFloat() ?: 0.0f
}
