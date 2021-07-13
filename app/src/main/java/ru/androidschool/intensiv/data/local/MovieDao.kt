package ru.androidschool.intensiv.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.data.entity.*
import ru.androidschool.intensiv.util.setDefaultThreads

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(actors: List<ActorEntity>): Completable

    fun save(movieAndActor: MovieAndActor) {
        save(movieAndActor.movie).setDefaultThreads().subscribe()
        save(movieAndActor.actors).setDefaultThreads().subscribe()
        saveJoins(movieAndActor.actors.map {
            MovieActorCrossRef(
                movieAndActor.movie.id,
                it.id
            )
        }).setDefaultThreads().subscribe()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveJoins(joins: List<MovieActorCrossRef>): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable

    @Delete
    fun delete(joins: List<MovieActorCrossRef>): Completable

    fun delete(movieAndActor: MovieAndActor) {
        delete(movieAndActor.movie).setDefaultThreads().subscribe()
        delete(movieAndActor.actors.map {
            MovieActorCrossRef(
                movieAndActor.movie.id,
                it.id
            )
        }).setDefaultThreads().subscribe()
    }

    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<Movie>>

    @Transaction
    @Query("SELECT * FROM Movies WHERE id=:id")
    fun getMovie(id: Int): Single<MovieAndActor>
}
