package ru.androidschool.intensiv.data

import kotlin.random.Random

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getSeries(): List<Series> = List(10){
        Series(
            title = "Spider-Man $it",
            rating = Random.nextDouble(0.0, 5.0),
            image = "https://www.whats-on-netflix.com/wp-content/uploads/2020/12/most-popular-series-on-netflix-for-2020.jpg"
        )
    }
}
