package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.util.convertRating

class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    var title: String?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "${BuildConfig.BASE_URL_IMAGE}$field"

    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f
        get() = convertRating(field)
}
