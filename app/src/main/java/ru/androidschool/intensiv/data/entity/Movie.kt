package ru.androidschool.intensiv.data.entity

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float? = 0.0f,
    @SerializedName("poster_path")
    val posterPath: String? = null
)
