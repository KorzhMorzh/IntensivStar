package ru.androidschool.intensiv

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.androidschool.intensiv.data.entity.*
import ru.androidschool.intensiv.data.mappers.convertMovieEntity
import ru.androidschool.intensiv.data.mappers.convertToMovie
import java.io.FileReader

class MappingUnitTest {
    lateinit var movieDetailsWithActors: MovieDetailsWithActors
    lateinit var movieAndActor: MovieAndActor

    @Before
    fun setUp() {
        val path =
            "E:\\projects\\AndroidProjects\\IntensivStar\\app\\src\\test\\java\\ru\\androidschool\\intensiv\\json"
        val gson = Gson()

        movieDetailsWithActors = MovieDetailsWithActors(
            movie = gson.fromJson(
                JsonReader(FileReader("$path\\movie.json")),
                MovieDetails::class.java
            ),
            actors = gson.fromJson(
                JsonReader(FileReader("$path\\movie_cast.json")),
                object : TypeToken<List<Actor>>() {}.type
            )
        )

        movieAndActor = MovieAndActor(
            movie = gson.fromJson(
                JsonReader(FileReader("$path\\movie_entity.json")),
                MovieEntity::class.java
            ),
            actors = gson.fromJson(
                JsonReader(FileReader("$path\\movie_cast_entity.json")),
                object : TypeToken<List<ActorEntity>>() {}.type
            )
        )
    }

    @Test
    fun `test mapping movie dto to the db entity`() {
        val mappedMovie = convertMovieEntity(movieDetailsWithActors)
        assertEquals(movieAndActor, mappedMovie)
    }

    @Test
    fun `map movie db entity to the dto`() {
        val dtoFromEntity = convertToMovie(movieAndActor)
        val expectedMovieDetails = movieDetailsWithActors.movie.copy(
            genres = movieDetailsWithActors.movie.genres?.map { Genre(id = null, name = it.name) },
            isFavourite = true
        )
        assertEquals(movieDetailsWithActors.copy(movie = expectedMovieDetails), dtoFromEntity)
    }
}
