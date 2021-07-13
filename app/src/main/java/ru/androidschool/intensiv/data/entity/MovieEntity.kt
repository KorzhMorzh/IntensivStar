package ru.androidschool.intensiv.data.entity

import androidx.room.*

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
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
data class ActorEntity(
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
        parentColumn = "id",
        entityColumn = "actorId",
        associateBy = Junction(
            MovieActorCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "actorId"
        )
    )
    val actors: List<ActorEntity>
)

@Entity(primaryKeys = ["movieId", "actorId"])
data class MovieActorCrossRef(
    val movieId: Int,
    val actorId: Int
)
