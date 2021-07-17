package ru.androidschool.intensiv.data.entity

import com.google.gson.annotations.SerializedName

class Series(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f,
    @SerializedName("poster_path")
    val posterPath: String? = null
)
