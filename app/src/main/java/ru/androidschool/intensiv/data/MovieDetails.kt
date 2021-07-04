package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.util.convertRating

data class MovieDetails(
    val genres: List<Genre>?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?
) {
    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f
        get() = convertRating(field)

    @SerializedName("poster_path")
    val posterPath: String? = null
        get() = "${BuildConfig.BASE_URL_IMAGE}$field"
}

data class Genre(
    val id: Int?,
    val name: String
)

data class ProductionCompany(
    val name: String
)

data class MovieCredits(
    val id: Int?,
    val cast: List<Actor>
)

data class Actor(
    val id: Int?,
    val name: String?
) {
    @SerializedName("profile_path")
    val profilePath: String? = null
        get() = "${BuildConfig.BASE_URL_IMAGE}$field"
}
