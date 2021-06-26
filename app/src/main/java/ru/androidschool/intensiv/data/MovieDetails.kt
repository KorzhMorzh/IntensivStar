package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.network.MovieApiClient.BASE_URL_IMAGE

data class MovieDetails(
    val genres: List<Genre>?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    val rating: Float
        get() = voteAverage?.div(2)?.toFloat() ?: 0.0f

    val poster: String
        get() = BASE_URL_IMAGE + posterPath
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
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) {
    val image: String
        get() = BASE_URL_IMAGE + profilePath
}
