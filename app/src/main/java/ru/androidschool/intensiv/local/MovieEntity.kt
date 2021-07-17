package ru.androidschool.intensiv.local

import androidx.room.*
import ru.androidschool.intensiv.data.Genre
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.data.MovieDetailsWithActors
import ru.androidschool.intensiv.data.ProductionCompany
import ru.androidschool.intensiv.util.Const

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int,
    val title: String?,
    val genres: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: String?,
    val releaseDate: String?,
    val voteAverage: Float?
)

@Entity
data class Actor(
    @PrimaryKey
    @ColumnInfo(name = "actorId")
    val id: Int,
    val name: String?,
    val profilePath: String?
)

data class MovieAndActor(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "actorId",
        associateBy = Junction(
            MovieActorCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<Actor>
)

@Entity(primaryKeys = ["movieId", "actorId"])
data class MovieActorCrossRef(
    val movieId: Int,
    val actorId: Int
)

fun convertMovieEntity(dto: MovieDetailsWithActors): MovieAndActor =
    MovieAndActor(
        MovieEntity(
            dto.movie.id ?: -1,
            dto.movie.title,
            dto.movie.genres?.joinToString(Const.DELIMITER) { it.name },
            dto.movie.overview,
            dto.movie.popularity,
            dto.movie.posterPath,
            dto.movie.productionCompanies?.joinToString(Const.DELIMITER) { it.name },
            dto.movie.releaseDate,
            dto.movie.voteAverage
        ),
        dto.actors.map { Actor(it.id, it.name, it.profilePath) }
    )

fun convertToMovie(movieAndActor: MovieAndActor): MovieDetailsWithActors =
    MovieDetailsWithActors(
        MovieDetails(
            id = movieAndActor.movie.id,
            title = movieAndActor.movie.title,
            genres = movieAndActor.movie.genres?.split(Const.DELIMITER)
                ?.map { Genre(id = null, name = it) },
            overview = movieAndActor.movie.overview,
            popularity = movieAndActor.movie.popularity,
            productionCompanies = movieAndActor.movie.productionCompanies?.split(Const.DELIMITER)
                ?.map { ProductionCompany(it) },
            posterPath = movieAndActor.movie.posterPath,
            voteAverage = movieAndActor.movie.voteAverage,
            releaseDate = movieAndActor.movie.releaseDate,
            isFavourite = true
        ),
        movieAndActor.actors.map {
            ru.androidschool.intensiv.data.Actor(
                it.id,
                it.name,
                it.profilePath
            )
        }
    )
