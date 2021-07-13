package ru.androidschool.intensiv.data.entity

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val genres: List<Genre>?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val isFavourite: Boolean = false
)

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
    val id: Int,
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String? = null
)

data class MovieDetailsWithActors(
    val movie: MovieDetails,
    val actors: List<Actor>
)
