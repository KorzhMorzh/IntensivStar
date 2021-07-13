package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.entity.*
import ru.androidschool.intensiv.util.Const

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
        dto.actors.map { ActorEntity(it.id, it.name, it.profilePath) }
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
            ru.androidschool.intensiv.data.entity.Actor(
                it.id,
                it.name,
                it.profilePath
            )
        }
    )
