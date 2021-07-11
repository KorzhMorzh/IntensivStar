package ru.androidschool.intensiv.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.util.setDefaultThreads

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: List<Actor>): Completable

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

    @Transaction
    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<MovieAndActor>>

    @Transaction
    @Query("SELECT * FROM Movies WHERE movieId=:id")
    fun getMovie(id: Int): Single<MovieAndActor>
}
