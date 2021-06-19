package ru.androidschool.intensiv.data

data class MovieDetails(
    val title: String,
    val image: String,
    val rating: Double,
    val description: String,
    val actors: List<Actor>,
    val studio: String,
    val genre: String,
    val year: String
)

data class Actor(
    val name: String,
    val image: String
)
