package ru.androidschool.intensiv.data

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

    fun getMovieDetails(): MovieDetails = MovieDetails(
        title = "Aquaman",
        image = "https://images.squarespace-cdn.com/content/v1/59d7e2c7e45a7c0ce235bb55/1545099053834-XKZ3BHSXCSY48E3EONXS/ke17ZwdGBToddI8pDm48kFdj1LU3QXNrC7XCDJRXSjl7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1US_GH6w34F4AbXQYP1mTMh6WZrJMPe9RIQ00FIMO_YvQxJ-BQGo94klLBA8TVf45lA/Aquaman-Film-Review.jpg",
        rating = 4.0,
        description = "In 1985 Maine, lighthouse keeper Thomas Curry rescues Atlanna, " +
                "the queen of the underwater nation of Atlantis, during a storm. " +
                "They eventually fall in love and have a son named Arthur, " +
                "who is born with the power to communicate with marine lifeforms. ",
        actors = listOf(
            Actor("Jason Momoa", "https://cdn.citatis.com/img/a/1c/15292.v6.jpg"),
            Actor("Nicole Kidman", "https://cdn.citatis.com/img/a/1c/15292.v6.jpg")
        ),
        studio = "Warner Bros.",
        genre = "Action, Adventure, Fantasy",
        year = "2018"
    )
}
