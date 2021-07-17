package ru.androidschool.intensiv.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.androidschool.intensiv.data.entity.*
import ru.androidschool.intensiv.util.setDefaultThreads

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(actors: List<ActorEntity>): Completable

    fun save(movieAndActor: MovieAndActor): Disposable {
        return save(movieAndActor.movie)
            .mergeWith(save(movieAndActor.actors))
            .andThen(saveJoins(movieAndActor.actors.map {
                MovieActorCrossRef(
                    movieAndActor.movie.id,
                    it.id
                )
            }))
            .setDefaultThreads()
            .subscribe()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveJoins(joins: List<MovieActorCrossRef>): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable

    @Delete
    fun delete(joins: List<MovieActorCrossRef>): Completable

    fun delete(movieAndActor: MovieAndActor): Disposable {
        return delete(movieAndActor.movie)
            .mergeWith(delete(movieAndActor.actors.map {
                MovieActorCrossRef(
                    movieAndActor.movie.id,
                    it.id
                )
            }))
            .setDefaultThreads()
            .subscribe()
    }

    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<Movie>>

    @Transaction
    @Query("SELECT * FROM Movies WHERE id=:id")
    fun getMovie(id: Int): Single<MovieAndActor>
}
