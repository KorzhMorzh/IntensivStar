package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class Series(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"

    val rating: Float
        get() = voteAverage?.div(2)?.toFloat() ?: 0.0f
}
