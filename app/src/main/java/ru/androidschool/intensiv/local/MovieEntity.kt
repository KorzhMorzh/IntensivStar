package ru.androidschool.intensiv.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.data.Genre
import ru.androidschool.intensiv.data.MovieDetailsWithActors
import ru.androidschool.intensiv.data.ProductionCompany

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String?,
    val genres: List<Genre>?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val actors: List<Actor>
)

fun convertMovie(dto: MovieDetailsWithActors): MovieEntity =
    MovieEntity(
        dto.movie.id?.toLong() ?: -1,
        dto.movie.title,
        dto.movie.genres,
        dto.movie.overview,
        dto.movie.popularity,
        dto.movie.posterPath,
        dto.movie.productionCompanies,
        dto.movie.releaseDate,
        dto.movie.voteAverage,
        dto.actors
    )
