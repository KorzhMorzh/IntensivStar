package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val genres: List<Genre>?,
    val id: Int?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("")
    val production_companies: List<ProductionCompany>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
){
    val rating: Float
        get() = voteAverage?.div(2)?.toFloat() ?: 0.0f
}

data class Genre(
    val id: Int?,
    val name: String
)

data class ProductionCompany(
    val name: String?
)